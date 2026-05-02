package com.onlineakimbank.documentservice.event;

import com.onlineakimbank.documentservice.dto.enums.State;
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
