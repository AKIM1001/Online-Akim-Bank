package com.onlineakimbank.adminservice.event;

import com.onlineakimbank.adminservice.dto.enums.*;
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
   private LoanType loanType;
   private PaymentType paymentType;
   private UserType userType;
   private Currency currency;
   private BigDecimal loanAmount;
   private Integer termMonths;
   private BigDecimal collateralValue;

    public String getUserId() {
       return null;
    }
}
