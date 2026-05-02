package com.onlineakimbank.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationEvent {

   private String correlationId;
   private String applicationId;
   private String accountId;
   private String loanType;
   private String paymentType;
   private String userType;
   private String currency;
   private BigDecimal loanAmount;
   private Integer termMonths;
   private BigDecimal collateralValue;

    public String getUserId() {
       return null;
    }
}
