package com.onlineakimbank.paymentservice.dto;

import com.onlineakimbank.paymentservice.entity.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDto(
        String paymentId,
        String accountId,
        UserType userType,
        PayerType payerType,
        PaymentStatus paymentStatus,
        PaymentMethod paymentMethod,
        State state,
        PaymentType paymentType,
        BigDecimal amount,
        Currency currency,
        Instant createdAt,
        Instant updatedAt,
        Instant executedAt
) {}
