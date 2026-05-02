package com.onlineakimbank.documentservice.event;

import com.onlineakimbank.documentservice.dto.enums.UserType;
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
