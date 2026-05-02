package com.onlineakimbank.cardservice.entity.main;

import com.onlineakimbank.cardservice.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sticker")
public class Sticker extends Card {

    @Column(nullable = false, updatable = false)
    private String linkedRealCardId;

    @Override
    protected int expiryYears() {
        return 3;
    }
}
