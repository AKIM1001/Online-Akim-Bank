package com.onlineakimbank.paymentservice.request;

import com.onlineakimbank.paymentservice.entity.enums.PaymentMethod;
import com.onlineakimbank.paymentservice.entity.enums.PaymentType;
import com.onlineakimbank.paymentservice.entity.enums.UserType;

import java.math.BigDecimal;

public record PaymentRequest(
        String accountId,
        UserType userType,
        PaymentMethod paymentMethod,
        PaymentType paymentType,
        BigDecimal amount,
        String currency
) {

}
