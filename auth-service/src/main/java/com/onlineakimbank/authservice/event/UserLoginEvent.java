package com.onlineakimbank.authservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginEvent {
    private String correlationId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
}
