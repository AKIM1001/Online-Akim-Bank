package com.onlineakimbank.cardservice.event.user;

import com.onlineakimbank.cardservice.entity.enums.UserRole;
import com.onlineakimbank.cardservice.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationEvent {
    private String correlationId;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private UserType userType;
    private String password;
}
