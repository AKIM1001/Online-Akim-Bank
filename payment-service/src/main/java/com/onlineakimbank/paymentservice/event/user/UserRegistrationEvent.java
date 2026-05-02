package com.onlineakimbank.paymentservice.event.user;

import com.onlineakimbank.paymentservice.entity.enums.UserRole;
import com.onlineakimbank.paymentservice.entity.enums.UserType;
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
    private UserType userType;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String password;
}
