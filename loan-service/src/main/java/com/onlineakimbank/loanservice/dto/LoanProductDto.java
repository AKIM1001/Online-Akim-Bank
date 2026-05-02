package com.onlineakimbank.loanservice.dto;

import com.onlineakimbank.loanservice.entity.enums.Currency;
import com.onlineakimbank.loanservice.entity.enums.LoanApplicationStatus;
import com.onlineakimbank.loanservice.entity.enums.LoanStatus;
import com.onlineakimbank.loanservice.entity.enums.LoanType;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanProductDto(
        String loanId,
        String accountId,
        String accountNumber,
        String applicationId,
        LoanType loanType,
        BigDecimal principalAmount,
        BigDecimal interestRate,
        Integer termMonths,
        BigDecimal monthlyPayment,
        BigDecimal remainingBalance,
        Currency currency,
        LoanStatus loanStatus,
        Instant issuedAt,
        Instant closedAt

) {}
