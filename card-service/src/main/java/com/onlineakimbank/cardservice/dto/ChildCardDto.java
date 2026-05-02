package com.onlineakimbank.cardservice.dto;

import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChildCardDto(
        String cardId,
        String userId,
        String accountId,
        String cardNumber,

        CardStatus cardStatus,
        CardNetwork cardNetwork,

        String parentCardId,
        String parentCardNumber,

        BigDecimal spendingLimit,
        BigDecimal spentAmount,
        BigDecimal remainingLimit,

        LocalDate issueDate,
        int expiryMonth,
        int expiryYear
) implements CardDto {
}

