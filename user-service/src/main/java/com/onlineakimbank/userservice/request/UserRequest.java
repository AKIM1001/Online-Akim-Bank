package com.onlineakimbank.userservice.request;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.validation.constraints.*;

public interface UserRequest {
    @NotBlank String email();
    @NotBlank String phoneNumber();
    @NotBlank @Size(min = 8) String password();
    @NotBlank String address();
    @NotNull Role role();
    @NotNull
    UserType userType();
}
