package com.onlineakimbank.userservice.dto;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;

import java.time.LocalDateTime;

public interface UserDto {
    String userId();
    String email();
    String phoneNumber();
    String address();
    String hashPassword();
    Role role();
    UserType userType();
    LocalDateTime createdDate();
    LocalDateTime updatedDate();

}

