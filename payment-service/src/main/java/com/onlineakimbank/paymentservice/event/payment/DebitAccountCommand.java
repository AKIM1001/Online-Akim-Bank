package com.onlineakimbank.paymentservice.event.payment;

import com.onlineakimbank.paymentservice.entity.enums.Currency;
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
    private Currency currency;
    private boolean success;
    private String reason;
}