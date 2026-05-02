package com.onlineakimbank.adminservice.event;

import com.onlineakimbank.adminservice.dto.enums.UserType;
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
    private String password;
}
