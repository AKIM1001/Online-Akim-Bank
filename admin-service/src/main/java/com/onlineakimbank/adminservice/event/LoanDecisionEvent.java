package com.onlineakimbank.adminservice.event;

import com.onlineakimbank.adminservice.dto.enums.LoanApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDecisionEvent {

    private String correlationId;
    private String applicationId;
    private String accountId;
    private LoanApplicationStatus status;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private Integer termMonths;
}
