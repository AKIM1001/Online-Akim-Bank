package com.onlineakimbank.cardservice.entity.main;

import com.onlineakimbank.cardservice.entity.Card;
import jakarta.persistence.*;

@Entity
@Table(name = "real_card")
public class RealCard extends Card {

    @Column(nullable = false)
    private String pinHash;

    @Column(nullable = false)
    private int pinAttempts;

    @Column(nullable = false)
    private boolean pinBlocked;

    @Override
    protected int expiryYears() {
        return 5;
    }
}
