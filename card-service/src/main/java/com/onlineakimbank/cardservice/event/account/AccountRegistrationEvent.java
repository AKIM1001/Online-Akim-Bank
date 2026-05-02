package com.onlineakimbank.cardservice.event.account;

import com.onlineakimbank.cardservice.entity.enums.AccountState;
import com.onlineakimbank.cardservice.entity.enums.AccountStatus;
import com.onlineakimbank.cardservice.entity.enums.UserRole;
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
    private UserRole userRole;
    private String accountNumber;
    private String accountType;
    private AccountStatus accountStatus;
    private AccountState accountState;

}