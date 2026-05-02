package com.onlineakimbank.loanservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanScheduleDto(
        String loanId,
        Integer totalInstallments,
        BigDecimal totalInterest,
        BigDecimal totalPayable,
        Instant createdAt

) {}
