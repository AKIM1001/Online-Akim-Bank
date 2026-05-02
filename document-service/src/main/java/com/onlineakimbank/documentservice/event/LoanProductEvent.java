package com.onlineakimbank.documentservice.event;

import com.onlineakimbank.documentservice.dto.enums.*;
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
    private LoanType loanType;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer termMonths;
    private BigDecimal monthlyPayment;
    private BigDecimal remainingBalance;
    private PaymentScheduleType paymentScheduleType;
    private Currency currency;
    private LoanApplicationStatus loanApplicationStatus;
    private LoanStatus loanStatus;
    private Instant issuedAt;
    private Instant closedAt;
}
