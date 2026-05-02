package com.onlineakimbank.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountCommand {

    private String correlationId;
    private String paymentId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private boolean success;
    private String reason;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
}
