package com.onlineakimbank.accountservice.request;

import com.onlineakimbank.accountservice.entity.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountRequest(
        @NotBlank String accountId,
        @NotBlank String userId,
        BigDecimal balance,
        @NotBlank String accountNumber,
        @NotBlank Currency currency,
        @NotBlank State state,
        @NotBlank Status status,
        @NotBlank UserRole role,
        @NotBlank AccountType accountType,
        @NotNull LocalDateTime createdAt,
        @NotNull LocalDateTime updatedAt
) {}
