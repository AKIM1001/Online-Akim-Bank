package com.onlineakimbank.accountservice.event.account;

import com.onlineakimbank.accountservice.entity.enums.Currency;
import com.onlineakimbank.accountservice.entity.enums.State;
import com.onlineakimbank.accountservice.entity.enums.Status;
import com.onlineakimbank.accountservice.entity.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegistrationEvent implements CorrelatableEvent {
    private String correlationId;
    private String userId;
    private String accountId;
    private BigDecimal balance;
    private Currency currency;
    private UserRole userRole;
    private String accountNumber;
    private String accountType;
    private Status status;
    private State state;

public AccountRegistrationEvent(
        String userId,
        String accountId,
        BigDecimal balance,
        Currency currency,
        UserRole userRole,
        String accountNumber,
        String accountType,
        Status status,
        State state
) {
    this.correlationId = java.util.UUID.randomUUID().toString();
    this.userId = userId;
    this.accountId = accountId;
    this.balance = balance;
    this.currency = currency;
    this.userRole = userRole;
    this.accountNumber = accountNumber;
    this.accountType = accountType;
    this.status = status;
    this.state = state;
  }
}