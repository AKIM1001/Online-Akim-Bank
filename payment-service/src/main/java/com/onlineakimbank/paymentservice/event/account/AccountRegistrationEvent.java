package com.onlineakimbank.paymentservice.event.account;


import com.onlineakimbank.paymentservice.entity.enums.Currency;
import com.onlineakimbank.paymentservice.entity.enums.State;
import com.onlineakimbank.paymentservice.entity.enums.Status;
import com.onlineakimbank.paymentservice.entity.enums.UserRole;
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