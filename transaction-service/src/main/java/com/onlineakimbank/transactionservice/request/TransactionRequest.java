package com.onlineakimbank.transactionservice.request;

import com.onlineakimbank.transactionservice.entity.enums.TransactionMethod;
import com.onlineakimbank.transactionservice.entity.enums.UserType;

import java.math.BigDecimal;
import java.util.Currency;

public record TransactionRequest(
        String accountId,
        String senderAccountId,
        String recipientAccountId,
        BigDecimal amount,
        Currency currency,
        String senderCardNumber,
        String recipientCardNumber,
        String paymentPurpose,
        TransactionMethod transactionMethod,
        UserType userType
) { }