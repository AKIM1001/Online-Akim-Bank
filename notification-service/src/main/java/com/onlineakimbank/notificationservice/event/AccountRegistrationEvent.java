package com.onlineakimbank.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegistrationEvent {
    private String correlationId;
    private String userId;
    private String accountId;
    private BigDecimal balance;
    private String currency;
    private String accountNumber;
    private String accountType;

public AccountRegistrationEvent(
        String userId,
        String accountId,
        BigDecimal balance,
        String currency,
        String accountNumber,
        String accountType
) {
    this.correlationId = java.util.UUID.randomUUID().toString();
    this.userId = userId;
    this.accountId = accountId;
    this.balance = balance;
    this.currency = currency;
    this.accountNumber = accountNumber;
    this.accountType = accountType;
  }
}