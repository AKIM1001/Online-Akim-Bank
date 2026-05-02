package com.onlineakimbank.authservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogoutEvent {
    private String correlationId;
    private String userId;
}
