package com.onlineakimbank.loanservice.dto;

import com.onlineakimbank.loanservice.entity.enums.InstallmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanInstallmentDto(
        String loanInstallmentId,
        String loanId,
        Integer installmentNumber,
        BigDecimal principalPart,
        BigDecimal interestPart,
        BigDecimal totalAmount,
        Instant dueDate,
        InstallmentStatus status,
        Instant paidAt
) {}
