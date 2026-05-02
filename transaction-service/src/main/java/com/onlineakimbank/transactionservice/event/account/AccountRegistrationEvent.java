package com.onlineakimbank.transactionservice.event.account;

import ch.qos.logback.core.status.Status;
import com.onlineakimbank.transactionservice.entity.enums.Currency;
import com.onlineakimbank.transactionservice.entity.enums.State;
import com.onlineakimbank.transactionservice.entity.enums.UserRole;
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
    private Currency currency;
    private UserRole userRole;
    private String accountNumber;
    private String accountType;
    private Status status;
    private State state;
}