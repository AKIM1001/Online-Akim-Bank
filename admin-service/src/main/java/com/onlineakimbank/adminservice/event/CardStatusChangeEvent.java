package com.onlineakimbank.adminservice.event;

import com.onlineakimbank.adminservice.dto.enums.CardStatus;
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
