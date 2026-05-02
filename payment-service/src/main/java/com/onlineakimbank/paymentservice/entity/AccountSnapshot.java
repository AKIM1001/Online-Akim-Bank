package com.onlineakimbank.paymentservice.entity;

import com.onlineakimbank.paymentservice.entity.enums.Currency;
import com.onlineakimbank.paymentservice.entity.enums.State;
import com.onlineakimbank.paymentservice.entity.enums.Status;
import com.onlineakimbank.paymentservice.entity.enums.UserRole;
import lombok.Data;

@Data
public class AccountSnapshot {

    private String accountId;
    private Currency currency;
    private State state;
    private Status status;
    private UserRole userRole;
}