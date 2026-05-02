package com.onlineakimbank.paymentservice.event.account;

import com.onlineakimbank.paymentservice.entity.enums.State;
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
    private State state;
}
