package com.onlineakimbank.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRegistrationEvent {
    private String correlationId;
    private String cardId;
    private String userId;
    private String AccountId;
    private String cardNumber;
    private String cvv;
    private BigDecimal balance;
    private LocalDate issueDate;
    private int expiryMonth;
    private int expiryYear;
    private String cardStatus;
    private String cardNetwork;
}