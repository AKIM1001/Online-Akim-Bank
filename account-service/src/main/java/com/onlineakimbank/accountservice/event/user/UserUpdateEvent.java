package com.onlineakimbank.accountservice.event.user;

import com.onlineakimbank.accountservice.entity.enums.UserRole;
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
    private UserRole role;
    private String password;
}
