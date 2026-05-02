package com.onlineakimbank.cardservice.event.account;

import com.onlineakimbank.cardservice.entity.enums.AccountState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStateChangeEvent {
    private String correlationId;
    private String userId;
    private String accountNumber;
    private String accountId;
    private AccountState accountState;

}
