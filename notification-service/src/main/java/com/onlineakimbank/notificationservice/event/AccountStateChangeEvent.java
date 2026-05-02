package com.onlineakimbank.notificationservice.event;

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
    private String state;
}
