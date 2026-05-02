package com.onlineakimbank.loanservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanPenaltyDto(
        String loanPenaltyId,
        String loanId,
        String installmentId,
        BigDecimal penaltyAmount,
        String reason,
        Boolean paid,
        Instant createdAt

) {}
