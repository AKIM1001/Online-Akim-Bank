package com.onlineakimbank.loanservice.dto;

import com.onlineakimbank.loanservice.entity.enums.CollateralType;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanDecisionDto(
        String loanDecisionId,
        String loanId,
        String loanApplicationId,
        Boolean approved,
        BigDecimal approvedAmount,
        Integer approvedTermMonths,
        BigDecimal interestRate,
        Integer creditScore,
        String rejectionReason,
        Instant decidedAt

) {}
