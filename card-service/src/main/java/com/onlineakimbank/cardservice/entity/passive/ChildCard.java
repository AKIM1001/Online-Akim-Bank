package com.onlineakimbank.cardservice.entity.passive;

import com.onlineakimbank.cardservice.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "child_card")
public class ChildCard extends Card {

    @Column(nullable = false)
    private String parentCardId;

    @Column(nullable = false)
    private String parentCardNumber;

    @Column(nullable = false)
    private BigDecimal spendingLimit;

    @Column(nullable = false)
    private BigDecimal spentAmount;

    @Override
    protected int expiryYears() {
        return 3;
    }

    void initChildFields() {
        if (spentAmount == null) {
            spentAmount = BigDecimal.ZERO;
        }
        if (spendingLimit == null) {
            spendingLimit = BigDecimal.ZERO;
        }
    }

}
