package com.onlineakimbank.userservice.dto;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record IndividualUserDto(
        String userId,
        String email,
        String phoneNumber,
        String address,
        String hashPassword,
        Role role,
        UserType userType,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,

        String firstName,
        String lastName,
        String passportSeries,
        String passportNumber,
        LocalDate birthday
) implements UserDto {}
