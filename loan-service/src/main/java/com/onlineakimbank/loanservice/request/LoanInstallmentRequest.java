package com.onlineakimbank.loanservice.request;

import java.math.BigDecimal;

public record LoanInstallmentRequest(
        String loanId,
        Integer installmentNumber,
        BigDecimal paymentAmount

) {}