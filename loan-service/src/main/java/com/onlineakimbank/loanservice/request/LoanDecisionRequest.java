package com.onlineakimbank.loanservice.request;

import java.math.BigDecimal;

public record LoanDecisionRequest(
        String loanApplicationId,
        Boolean approved,
        BigDecimal approvedAmount,
        Integer approvedTermMonths,
        BigDecimal interestRate,
        Integer creditScore,
        String rejectionReason

) {}