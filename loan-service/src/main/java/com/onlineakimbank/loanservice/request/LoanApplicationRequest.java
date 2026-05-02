package com.onlineakimbank.loanservice.request;

import com.onlineakimbank.loanservice.entity.enums.LoanPurpose;
import com.onlineakimbank.loanservice.entity.enums.LoanType;
import com.onlineakimbank.loanservice.entity.enums.UserType;

import java.math.BigDecimal;

public record LoanApplicationRequest(
        String loanApplicationId,
        String accountId,
        String accountNumber,
        UserType userType,
        LoanType loanType,
        BigDecimal requestedAmount,
        LoanPurpose purpose,
        Integer termMonths

) {}
