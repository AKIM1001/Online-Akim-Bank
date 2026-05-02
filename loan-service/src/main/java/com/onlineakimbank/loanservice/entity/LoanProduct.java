package com.onlineakimbank.loanservice.entity;

import com.onlineakimbank.loanservice.entity.enums.Currency;
import com.onlineakimbank.loanservice.entity.enums.LoanApplicationStatus;
import com.onlineakimbank.loanservice.entity.enums.LoanStatus;
import com.onlineakimbank.loanservice.entity.enums.LoanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_product")
public class LoanProduct {

    @Id
    @Column(name = "loan_id", length = 24, nullable = false)
    private String loanId;

    @Column(nullable = false, updatable = false)
    private String accountId;

    @Column(nullable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false, updatable = false)
    private String applicationId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private BigDecimal principalAmount; // сумма

    private BigDecimal interestRate;  // ставка

    private Integer termMonths;

    private BigDecimal monthlyPayment;

    private BigDecimal remainingBalance;

    private Currency currency;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanApplicationStatus;

    private Instant issuedAt;

    private Instant closedAt;

}
