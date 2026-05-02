package com.onlineakimbank.documentservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEvent {
    private String correlationId;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
