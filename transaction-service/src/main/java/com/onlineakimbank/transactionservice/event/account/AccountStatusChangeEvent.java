package com.onlineakimbank.transactionservice.event.account;

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
    private String status;
}
