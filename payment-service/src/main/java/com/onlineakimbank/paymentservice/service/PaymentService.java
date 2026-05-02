package com.onlineakimbank.paymentservice.service;

import com.onlineakimbank.paymentservice.config.PaymentValidationConfig;
import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.AccountSnapshot;
import com.onlineakimbank.paymentservice.entity.Payment;
import com.onlineakimbank.paymentservice.event.payment.DebitAccountCommand;
import com.onlineakimbank.paymentservice.entity.enums.PayerType;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import com.onlineakimbank.paymentservice.repository.AccountSnapshotRepository;
import com.onlineakimbank.paymentservice.repository.PaymentCassandraRepository;
import com.onlineakimbank.paymentservice.repository.PaymentRedisRepository;
import com.onlineakimbank.paymentservice.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRedisRepository paymentRepository;
    private final PaymentCassandraRepository cassandraRepository;
    private final AccountSnapshotRepository accountSnapshotRepository;
    private final KafkaTemplate<String, DebitAccountCommand> kafkaTemplate;

    public PaymentDto createPayment(PaymentRequest request) {

        AccountSnapshot account = accountSnapshotRepository
                .findById(request.accountId())
                .orElseThrow(() -> new IllegalStateException("[Account not found]"));

        PaymentValidationConfig.validateAccountState(account.getState());

        PayerType payerType =
                account.getUserRole().name().equals("[INDIVIDUAL]")
                        ? PayerType.INDIVIDUAL
                        : PayerType.BUSINESS;

        PaymentValidationConfig.validateBusinessRules(
                payerType,
                request.paymentMethod(),
                request.paymentType()
        );

        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setAccountId(account.getAccountId());
        payment.setPayerType(payerType);
        payment.setPaymentMethod(request.paymentMethod());
        payment.setPaymentType(request.paymentType());
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setAmount(request.amount());
        payment.setCurrency(account.getCurrency());

        PaymentDto saved = paymentRepository.save(payment);

        paymentRepository.updateStatus(saved.paymentId(), PaymentStatus.VALIDATED);

        DebitAccountCommand command = new DebitAccountCommand(
                UUID.randomUUID().toString(),
                saved.paymentId(),
                saved.accountId(),
                saved.amount(),
                saved.currency(),
                false,
                null
        );

        kafkaTemplate.send(
                "debit-account-topic",
                saved.paymentId(),
                command
        );

        paymentRepository.updateStatus(saved.paymentId(), PaymentStatus.SENT);

        return saved;
    }

    public void finalizePayment(String paymentId, boolean success, String reason) {

        PaymentDto dto = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new IllegalStateException("[Payment not found in Redis]"));

        if (success) {
            paymentRepository.updateStatus(paymentId, PaymentStatus.COMPLETED);
        } else {
            paymentRepository.updateStatus(paymentId, PaymentStatus.FAILED);
        }

        Payment entity = mapDtoToEntity(dto);
        cassandraRepository.save(entity);

        paymentRepository.deleteByPaymentId(paymentId);
    }

    private Payment mapDtoToEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setPaymentId(dto.paymentId());
        payment.setAccountId(dto.accountId());
        payment.setPayerType(dto.payerType());
        payment.setPaymentMethod(dto.paymentMethod());
        payment.setPaymentType(dto.paymentType());
        payment.setPaymentStatus(dto.paymentStatus());
        payment.setAmount(dto.amount());
        payment.setCurrency(dto.currency());
        payment.setCreatedAt(dto.createdAt());
        payment.setUpdatedAt(dto.updatedAt());
        payment.setExecutedAt(dto.executedAt());
        return payment;
    }
}