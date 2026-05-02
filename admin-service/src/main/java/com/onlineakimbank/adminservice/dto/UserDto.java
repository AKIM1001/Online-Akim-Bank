package com.onlineakimbank.adminservice.dto;

import com.onlineakimbank.adminservice.dto.enums.Role;
import com.onlineakimbank.adminservice.dto.enums.UserType;

import java.time.LocalDateTime;

public record UserDto(
    String userId,
    String email,
    String phoneNumber,
    String address,
    String hashPassword,
    Role role,
    UserType userType,
    LocalDateTime createdDate,
    LocalDateTime updatedDate

) {}