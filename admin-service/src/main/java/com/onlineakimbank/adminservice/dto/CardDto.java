package com.onlineakimbank.adminservice.dto;

import com.onlineakimbank.adminservice.dto.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardDto(String cardId,
                      String accountId,
                      String cardNumber,
                      CardStatus cardStatus,
                      String cvv,
                      BigDecimal balance,
                      LocalDate issueDate,
                      int expiryMonth,
                      int expiryYear) {}

