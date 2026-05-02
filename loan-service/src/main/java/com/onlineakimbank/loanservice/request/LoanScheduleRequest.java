package com.onlineakimbank.loanservice.request;

public record LoanScheduleRequest(
        String loanId,
        Integer totalInstallments
) {}
