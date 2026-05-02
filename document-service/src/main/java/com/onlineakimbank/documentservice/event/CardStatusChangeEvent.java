package com.onlineakimbank.documentservice.event;

import com.onlineakimbank.documentservice.dto.enums.CardStatus;
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
