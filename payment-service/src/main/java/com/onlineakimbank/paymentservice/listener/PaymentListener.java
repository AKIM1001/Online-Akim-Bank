package com.onlineakimbank.paymentservice.listener;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.Payment;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import com.onlineakimbank.paymentservice.entity.enums.State;
import com.onlineakimbank.paymentservice.event.payment.DebitAccountCommand;
import com.onlineakimbank.paymentservice.event.user.UserRegistrationEvent;
import com.onlineakimbank.paymentservice.event.user.UserUpdateEvent;
import com.onlineakimbank.paymentservice.repository.PaymentCassandraRepository;
import com.onlineakimbank.paymentservice.repository.PaymentRedisRepository;
import com.onlineakimbank.paymentservice.service.CashbackService;
import com.onlineakimbank.paymentservice.service.PaymentManagementService;
import com.onlineakimbank.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentRedisRepository redisRepository;
    private final PaymentCassandraRepository cassandraRepository;

    @KafkaListener(
            topics = "payment-topic",
            groupId = "payment-service",
            containerFactory = "kafkaListenerContainerFactoryPayment"
    )
    public void handlePaymentStatus(DebitAccountCommand event) {

        Payment payment = cassandraRepository.findById(event.getPaymentId())
                .orElseThrow(() -> new IllegalStateException("[Payment not found: " + event.getPaymentId() + "]"));

        State payerState = payment.getState();

        boolean invalidPayer = payerState == State.BLOCKED ||
                payerState == State.CLOSED ||
                payerState == State.FROZEN;

        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED ||
                payment.getPaymentStatus() == PaymentStatus.FAILED) {
            log.info("Payment {} already finalized with status {}", payment.getPaymentId(), payment.getPaymentStatus());
            return;
        }

        if (invalidPayer) {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            redisRepository.updateStatus(payment.getPaymentId(), PaymentStatus.FAILED);
            cassandraRepository.save(payment);

            log.warn("Payment {} failed: payer account state={}", payment.getPaymentId(), payerState);
            return;
        }

        payment.setPaymentStatus(PaymentStatus.PROCESSING);
        redisRepository.updateStatus(payment.getPaymentId(), PaymentStatus.PROCESSING);
        cassandraRepository.save(payment);

        log.info("Payment {} marked as PROCESSING", payment.getPaymentId());
    }

    @KafkaListener(
            topics = "user-registration",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryUserRegistration"
    )
    public void processUserRegistration(UserRegistrationEvent event) {
        log.info("Received user registration event: {}", event.getUserId());
    }

    @KafkaListener(
            topics = "user-update",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryUserUpdate"
    )
    public void processUserUpdate(UserUpdateEvent event) {
        log.info("Received user update event: {}", event.getUserId());
    }
}