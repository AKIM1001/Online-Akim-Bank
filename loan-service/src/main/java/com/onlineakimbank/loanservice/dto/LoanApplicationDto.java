package com.onlineakimbank.loanservice.dto;

import com.onlineakimbank.loanservice.entity.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanApplicationDto(
        String loanApplicationId,
        String accountId,
        String accountNumber,
        UserType userType,
        LoanType loanType,
        LoanApplicationStatus loanApplicationStatus,
        BigDecimal requestedAmount,
        Currency currency,
        LoanPurpose purpose,
        Integer termMonths,
        Instant createdAt,
        Instant updatedAt

) {}
