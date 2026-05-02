package com.onlineakimbank.accountservice.event.account;

import com.onlineakimbank.accountservice.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusChangeEvent implements CorrelatableEvent {
    private String correlationId;
    private String userId;
    private String accountNumber;
    private String accountId;
    private Status status;
}
