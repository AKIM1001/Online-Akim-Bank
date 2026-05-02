package com.onlineakimbank.userservice.event;

import com.onlineakimbank.userservice.entity.enums.Role;
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
    private Role role;
    private String password;
}
