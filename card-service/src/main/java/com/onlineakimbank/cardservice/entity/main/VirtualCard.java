package com.onlineakimbank.cardservice.entity.main;

import com.onlineakimbank.cardservice.entity.Card;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "virtual_card")
public class VirtualCard extends Card {

    @Override
    protected int expiryYears() {
        return 3;
    }
}
