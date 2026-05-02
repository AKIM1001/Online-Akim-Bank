package com.onlineakimbank.loanservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "loan_penalties")
public class LoanPenalty {

    @Id
    @Column(name = "loan_penalty_id", length = 24, nullable = false)
    private String loanPenaltyId;

    private String loanId;

    private String installmentId;

    private BigDecimal penaltyAmount;

    private String reason;

    private Boolean paid;

    private Instant createdAt;

}
