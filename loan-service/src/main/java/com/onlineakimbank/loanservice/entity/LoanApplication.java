package com.onlineakimbank.loanservice.entity;

import com.onlineakimbank.loanservice.entity.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_application")
public class LoanApplication {

    @Id
    @Column(name = "loan_application_id", length = 24, nullable = false)
    private String loanApplicationId;

    @Column(name = "loan_id", length = 24, nullable = false)
    private String loanId;

    @Column(nullable = false, updatable = false)
    private String accountId;

    @Column(nullable = false, updatable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanApplicationStatus loanApplicationStatus;

    private BigDecimal requestedAmount;
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private LoanPurpose purpose;

    private Integer termMonths;

    private Instant createdAt;
    private Instant updatedAt;
}
