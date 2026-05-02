package com.onlineakimbank.loanservice.entity;

import com.onlineakimbank.loanservice.entity.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "loan_collaterals")
public class LoanCollateral { // залог

    @Id
    @Column(name = "loan_collaterals_id", length = 24, nullable = false)
    private String loanCollateralsId;

    private String loanId;

    private String accountId;

    private String description;

    @Enumerated(EnumType.STRING)
    private CollateralType collateralType;

    private BigDecimal estimatedValue;

    private Instant registeredAt;

}
