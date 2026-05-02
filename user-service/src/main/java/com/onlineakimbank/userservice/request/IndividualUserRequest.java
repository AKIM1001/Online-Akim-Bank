package com.onlineakimbank.userservice.request;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record IndividualUserRequest(
        @NotBlank String email,
        @NotBlank String phoneNumber,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String address,
        @NotNull Role role,
        @NotNull UserType userType,

        @NotBlank String firstName,
        @NotBlank String lastName,
        @Pattern(regexp = "^\\d{2}\\s?\\d{2}$") String passportSeries,
        @Pattern(regexp = "^\\d{6}$") String passportNumber,
        @NotNull LocalDate birthday
) implements UserRequest {}

