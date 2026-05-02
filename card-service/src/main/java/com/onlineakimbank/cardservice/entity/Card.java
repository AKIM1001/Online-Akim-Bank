package com.onlineakimbank.cardservice.entity;

import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Card {

    @Id
    private String cardId;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private String accountId;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 3)
    private String cvv;

    private BigDecimal balance;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private int expiryMonth;

    @Column(nullable = false)
    private int expiryYear;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @Enumerated(EnumType.STRING)
    private CardNetwork cardNetwork;

    protected abstract int expiryYears();

    @PrePersist
    void prePersist() {

        if (cardId == null || cardId.isBlank()) {
            cardId = UUID.randomUUID().toString();
        }

        if (issueDate == null) {
            issueDate = LocalDate.now();
        }

        if (cardStatus == null) {
            cardStatus = CardStatus.ACTIVE;
        }

        LocalDate expiry = issueDate.plusYears(expiryYears());
        expiryYear = expiry.getYear();
        expiryMonth = expiry.getMonthValue();
    }

}
