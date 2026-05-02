package com.onlineakimbank.cardservice.event.account;

import com.onlineakimbank.cardservice.entity.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusChangeEvent {
    private String correlationId;
    private String userId;
    private String accountNumber;
    private String accountId;
    private AccountStatus accountStatus;
}
