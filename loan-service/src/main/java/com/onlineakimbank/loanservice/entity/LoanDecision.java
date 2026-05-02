package com.onlineakimbank.loanservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;


@Data
public class LoanDecision {

    @Id
    @Column(name = "loan_id", length = 24, nullable = false)
    private String loanId;

    @Column(name = "loan_application_id", length = 24, nullable = false)
    private String loanApplicationId;
    private String applicationId;

    private Boolean approved;

    private BigDecimal approvedAmount;

    private Integer approvedTermMonths;

    private BigDecimal interestRate;

    private Integer creditScore;

    private String rejectionReason;

    private Instant decidedAt;
}
