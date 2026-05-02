package com.onlineakimbank.loanservice.request;

public record LoanPenaltyRequest(
        String loanId,
        String installmentId
) {}