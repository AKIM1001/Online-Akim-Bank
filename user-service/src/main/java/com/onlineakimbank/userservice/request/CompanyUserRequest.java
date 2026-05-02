package com.onlineakimbank.userservice.request;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.validation.constraints.*;

public record CompanyUserRequest(
        @NotBlank String email,
        @NotBlank String phoneNumber,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String address,
        @NotNull Role role,
        @NotNull UserType userType,

        @NotBlank String companyName,
        @Pattern(regexp = "^\\d{10}$") String inn,
        @Pattern(regexp = "^\\d{9}$") String kpp,
        @NotBlank String legalAddress
) implements UserRequest {}
