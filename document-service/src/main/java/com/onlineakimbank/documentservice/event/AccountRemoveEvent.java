package com.onlineakimbank.documentservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRemoveEvent {
    private String correlationId;
    private String userId;
    private String accountId;
    private String accountNumber;
}
