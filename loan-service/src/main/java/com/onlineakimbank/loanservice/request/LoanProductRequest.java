package com.onlineakimbank.loanservice.request;

import com.onlineakimbank.loanservice.entity.enums.LoanType;

import java.math.BigDecimal;

public record LoanProductRequest(
        String loanId,
        String accountId,
        String accountNumber,
        String applicationId,
        LoanType loanType,
        BigDecimal principalAmount,
        BigDecimal interestRate,
        Integer termMonths

) {}
