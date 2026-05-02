package com.onlineakimbank.accountservice.dto;

import com.onlineakimbank.accountservice.entity.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDto(
        String accountId,
        String userId,
        BigDecimal balance,
        String accountNumber,
        Currency currency,
        Status status,
        State state,
        UserRole role,
        AccountType accountType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}