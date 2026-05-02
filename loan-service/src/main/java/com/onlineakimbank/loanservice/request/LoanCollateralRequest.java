package com.onlineakimbank.loanservice.request;

import com.onlineakimbank.loanservice.entity.enums.CollateralType;

import java.math.BigDecimal;

public record LoanCollateralRequest(
        String loanCollateralId,
        String loanId,
        String accountId,
        CollateralType collateralType,
        String description,
        BigDecimal estimatedValue

) {}