package com.onlineakimbank.adminservice.dto;

import com.onlineakimbank.adminservice.dto.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDto(
        String paymentId,
        String accountId,
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
