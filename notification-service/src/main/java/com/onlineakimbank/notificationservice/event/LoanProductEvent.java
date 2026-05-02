package com.onlineakimbank.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProductEvent {

    private String correlationId;
    private String accountId;
    private String productId;
    private String loanId;
    private String accountNumber;
    private String loanType;
    private BigDecimal principalAmount; // сумма
    private BigDecimal interestRate;  // ставка
    private Integer termMonths;
    private BigDecimal monthlyPayment;
    private BigDecimal remainingBalance;
    private String paymentScheduleType;
    private String currency;
    private String loanApplicationStatus;
    private String loanStatus;
    private Instant issuedAt;
    private Instant closedAt;
}
