package com.onlineakimbank.loanservice.dto;

import com.onlineakimbank.loanservice.entity.enums.CollateralType;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanCollateralDto(
        String loanCollateralId,
        String loanId,
        String accountId,
        CollateralType collateralType,
        String description,
        BigDecimal estimatedValue,
        Instant registeredAt
) {}
