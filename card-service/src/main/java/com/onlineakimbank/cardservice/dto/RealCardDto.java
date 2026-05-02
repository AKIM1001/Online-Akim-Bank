package com.onlineakimbank.cardservice.dto;

import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RealCardDto(
        String cardId,
        String userId,
        String accountId,
        String cardNumber,
        LocalDate issueDate,
        BigDecimal balance,
        int expiryMonth,
        int expiryYear,
        CardStatus cardStatus,
        CardNetwork cardNetwork,
        boolean pinSet,
        boolean delivered
) implements CardDto {

}

