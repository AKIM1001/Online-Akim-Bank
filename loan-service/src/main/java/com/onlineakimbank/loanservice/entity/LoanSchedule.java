package com.onlineakimbank.loanservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_schedule")
public class LoanSchedule {

    @Id
    private String loanId;

    private Integer totalInstallments;

    private BigDecimal totalInterest;

    private BigDecimal totalPayable;

    private Instant createdAt;
}
