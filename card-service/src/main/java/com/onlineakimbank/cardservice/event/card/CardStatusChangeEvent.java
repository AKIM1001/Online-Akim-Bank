package com.onlineakimbank.cardservice.event.card;

import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusChangeEvent {
    private String correlationId;
    private String cardId;
    private String accountId;
    private String cardNumber;
    private CardStatus cardStatus;

    public String getCardId() {
        return cardId;
    }

    public CardStatus getStatus() {
        return cardStatus;
    }
    public CardStatus updateStatus() {
        return cardStatus;
    }
}
