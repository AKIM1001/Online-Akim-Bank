package com.onlineakimbank.adminservice.dto;

import com.onlineakimbank.adminservice.dto.enums.TransactionMethod;
import com.onlineakimbank.adminservice.dto.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

public record TransactionDto(
        String transactionId,
        String transactionNumber,
        String accountId,
        String senderAccountId,
        String recipientAccountId,
        String senderAccountNumber,
        String recipientAccountNumber,
        String senderCardNumber,
        String recipientCardNumber,
        BigDecimal senderBalanceBefore,
        BigDecimal senderBalanceAfter,
        BigDecimal recipientBalanceBefore,
        BigDecimal recipientBalanceAfter,
        BigDecimal recipientCardBalanceBefore,
        BigDecimal recipientCardBalanceAfter,
        TransactionMethod transactionMethod,
        TransactionStatus transactionStatus,
        Currency currency,
        BigDecimal amount,
        String paymentPurpose,
        Instant createdAt,
        Instant updatedAt,
        Instant executedAt
) {}
