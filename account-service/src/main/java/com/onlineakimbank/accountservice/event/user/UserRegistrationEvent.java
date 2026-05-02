package com.onlineakimbank.accountservice.event.user;

import com.onlineakimbank.accountservice.entity.enums.UserRole;
import com.onlineakimbank.accountservice.entity.enums.UserType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationEvent {
    private String correlationId;
    private String userId;
    private String email;
    private UserType userType;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String password;
}
