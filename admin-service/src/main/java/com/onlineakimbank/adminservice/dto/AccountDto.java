package com.onlineakimbank.adminservice.dto;

import com.onlineakimbank.adminservice.dto.enums.Currency;
import com.onlineakimbank.adminservice.dto.enums.Role;
import com.onlineakimbank.adminservice.dto.enums.State;
import com.onlineakimbank.adminservice.dto.enums.UserType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDto(
        String accountId,
        String userId,
        BigDecimal balance,
        String accountNumber,
        Currency currency,
        State state,
        Role role,
        UserType accountType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}