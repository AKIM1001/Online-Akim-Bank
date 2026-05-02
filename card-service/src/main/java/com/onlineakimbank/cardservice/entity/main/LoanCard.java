package com.onlineakimbank.cardservice.entity.main;


import com.onlineakimbank.cardservice.entity.Card;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DiscriminatorValue("LOAN")
public class LoanCard extends Card {

    @Column(nullable = false)
    private BigDecimal loanAmount;


    @Column(nullable = false)
    private BigDecimal remainingDebt;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate noInterestUntil;


    private Instant frozenAt;
    private Instant blockedAt;

    @Column(nullable = false)
    private BigDecimal interestRate;

    @Override
    protected int expiryYears() {
        return 3;
    }

    void prePersistLoan() {
        if (loanAmount == null) loanAmount = BigDecimal.ZERO;
        if (remainingDebt == null) remainingDebt = loanAmount;
        if (noInterestUntil == null) noInterestUntil = LocalDate.now();
        if (interestRate == null) interestRate = BigDecimal.valueOf(12);
        if (dueDate == null) dueDate = LocalDate.now().plusMonths(12);
    }

    public void setFrozenAt(Instant frozenAt) { this.frozenAt = frozenAt; }
    public Instant getFrozenAt() { return frozenAt; }

    public void setBlockedAt(Instant blockedAt) { this.blockedAt = blockedAt; }
    public Instant getBlockedAt() { return blockedAt; }
}
