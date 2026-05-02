package com.onlineakimbank.userservice.event;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import com.onlineakimbank.userservice.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationEvent implements UserRequest {
    private String correlationId;
    private String userId;
    private String email;
    private UserType type;
    private String firstName;
    private String lastName;
    private Role role;
    private String password;

    @Override
    public String email() {
        return "";
    }

    @Override
    public String phoneNumber() {
        return "";
    }

    @Override
    public String password() {
        return "";
    }

    @Override
    public String address() {
        return "";
    }

    @Override
    public Role role() {
        return null;
    }

    @Override
    public UserType userType() {
        return null;
    }
}
