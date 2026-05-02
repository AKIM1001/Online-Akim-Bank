package com.onlineakimbank.paymentservice.service;

import com.onlineakimbank.paymentservice.config.BusinessCashbackConfig;
import com.onlineakimbank.paymentservice.config.IndividualCashbackConfig;
import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.AccountSnapshot;
import com.onlineakimbank.paymentservice.entity.enums.*;
import com.onlineakimbank.paymentservice.event.payment.DebitAccountCommand;
import com.onlineakimbank.paymentservice.repository.AccountSnapshotRepository;
import com.onlineakimbank.paymentservice.repository.PaymentCassandraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CashbackService {

    private final IndividualCashbackConfig individualConfig;
    private final BusinessCashbackConfig businessConfig;
    private final PaymentCassandraRepository cashbackRepository;
    private final AccountSnapshotRepository accountSnapshotRepository;
    private final KafkaTemplate<String, DebitAccountCommand> kafkaTemplate;

    public void processCashback(PaymentDto payment) {

        if (payment.paymentStatus() != PaymentStatus.COMPLETED) {
            return;
        }

        AccountSnapshot account = accountSnapshotRepository
                .findById(payment.accountId())
                .orElseThrow(() -> new IllegalStateException("[Account not found]"));

        Status status = account.getStatus();
        BigDecimal percent = BigDecimal.ZERO;

        if (payment.payerType() == PayerType.INDIVIDUAL) {

            if (payment.currency() != Currency.RUB) return;

            List<IndividualCashbackCategory> active =
                    cashbackRepository.getIndividualCategories(payment.accountId())
                            .map(acc -> acc.getIndividualCategories())
                            .orElse(Set.of())
                            .stream()
                            .map(IndividualCashbackCategory::valueOf)
                            .toList();

            IndividualCashbackCategory category =
                    IndividualCashbackCategory.valueOf(payment.paymentType().name());

            if (!active.contains(category)) return;

            percent = individualConfig.resolvePercent(category, status, payment.currency());

        } else {

            List<BusinessCashbackCategory> active =
                    cashbackRepository.getBusinessCategories(payment.accountId())
                            .map(acc -> acc.getBusinessCategories())
                            .orElse(Set.of())
                            .stream()
                            .map(BusinessCashbackCategory::valueOf)
                            .toList();

            BusinessCashbackCategory category =
                    BusinessCashbackCategory.valueOf(payment.paymentType().name());

            if (!active.contains(category)) return;

            percent = businessConfig.resolvePercent(category, status);
        }

        if (percent.compareTo(BigDecimal.ZERO) <= 0) return;

        BigDecimal cashbackAmount = payment.amount()
                .multiply(percent)
                .divide(BigDecimal.valueOf(100));

        cashbackRepository.addCashback(payment.accountId(), cashbackAmount);

        DebitAccountCommand command = new DebitAccountCommand();

        command.setCorrelationId(UUID.randomUUID().toString());
        command.setPaymentId(payment.paymentId());
        command.setAccountId(payment.accountId());
        command.setAmount(cashbackAmount);
        command.setCurrency(payment.currency()
        );

        kafkaTemplate.send(
                "credit-account-topic",
                payment.accountId(),
                command
        );
    }
}