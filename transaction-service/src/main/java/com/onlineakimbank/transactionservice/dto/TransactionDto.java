package com.onlineakimbank.transactionservice.dto;

import com.onlineakimbank.transactionservice.entity.enums.TransactionMethod;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import com.onlineakimbank.transactionservice.entity.enums.UserType;
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
        UserType userType,
        Currency currency,
        BigDecimal amount,
        String paymentPurpose,
        Instant createdAt,
        Instant updatedAt,
        Instant executedAt
) { }
