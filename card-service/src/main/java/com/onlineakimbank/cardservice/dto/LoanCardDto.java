package com.onlineakimbank.cardservice.dto;

import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanCardDto(
        String cardId,
        String userId,
        String accountId,
        String cardNumber,
        CardStatus cardStatus,
        CardNetwork cardNetwork,
        LocalDate issueDate,
        int expiryMonth,
        int expiryYear,

        BigDecimal loanAmount,
        BigDecimal remainingDebt,
        LocalDate noInterestUntil,
        BigDecimal interestRate,
        LocalDate expiryDate

) implements CardDto{
}
