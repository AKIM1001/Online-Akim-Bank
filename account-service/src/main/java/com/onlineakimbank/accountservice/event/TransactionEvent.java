package com.onlineakimbank.accountservice.event;

import com.onlineakimbank.accountservice.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {

    private String correlationId;
    private String transactionId;

    private String accountId;
    private boolean holdFunds;
    private String metadata;

    private String senderAccountId;
    private String recipientAccountId;

    private BigDecimal senderBalanceAfter;
    private BigDecimal recipientBalanceAfter;

    private BigDecimal amount;

    private Currency currency;
}
