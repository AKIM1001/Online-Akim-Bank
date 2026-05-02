package com.onlineakimbank.cardservice.event.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRemoveEvent {
    private String correlationId;
    private String cardId;
    private String userId;
    private String accountId;
}
