package com.onlineakimbank.authservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRemovedEvent {
    private String correlationId;
    private String userId;
    private boolean userExists;
}
