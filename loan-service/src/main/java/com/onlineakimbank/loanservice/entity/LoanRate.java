package com.onlineakimbank.loanservice.entity;

import com.onlineakimbank.loanservice.entity.enums.Currency;
import com.onlineakimbank.loanservice.entity.enums.LoanType;
import com.onlineakimbank.loanservice.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "loan_rates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanRate {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal centralBankRate;

    private BigDecimal bankMargin;

    private BigDecimal minRate;

    private BigDecimal maxRate;

    private BigDecimal maxAmount;

    private Integer maxTermMonths;

    private Boolean collateralRequired;

    private Instant createdAt;
}