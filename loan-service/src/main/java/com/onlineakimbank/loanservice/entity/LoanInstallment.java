package com.onlineakimbank.loanservice.entity;

import com.onlineakimbank.loanservice.entity.enums.InstallmentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "loan_installments")
public class LoanInstallment {

    @Id
    @Column(name = "loan_installment_id", length = 24, nullable = false)
    private String loanInstallmentId;

    private String loanId;

    private Integer installmentNumber;

    private BigDecimal principalPart;

    private BigDecimal interestPart;

    private BigDecimal totalAmount;

    private Instant dueDate;

    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    private Instant paidAt;

}
