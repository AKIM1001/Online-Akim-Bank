package com.onlineakimbank.accountservice.event.account;

import com.onlineakimbank.accountservice.entity.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStateChangeEvent implements CorrelatableEvent {
    private String correlationId;
    private String userId;
    private String accountNumber;
    private String accountId;
    private State state;
}
