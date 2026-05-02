package com.onlineakimbank.authservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRemoveEvent {
    private String correlationId;
    private String userId;
}
