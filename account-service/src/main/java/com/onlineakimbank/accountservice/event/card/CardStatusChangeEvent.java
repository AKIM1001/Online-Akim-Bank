package com.onlineakimbank.accountservice.event.card;

import com.onlineakimbank.accountservice.entity.enums.CardStatus;
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
}
