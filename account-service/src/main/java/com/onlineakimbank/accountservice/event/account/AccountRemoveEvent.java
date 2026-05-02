package com.onlineakimbank.accountservice.event.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRemoveEvent implements CorrelatableEvent {
    private String correlationId;
    private String userId;
    private String accountId;
    private String accountNumber;
}
