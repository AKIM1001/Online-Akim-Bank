package com.onlineakimbank.paymentservice.entity.enums;

public enum UserType {

    INDIVIDUAL(PayerType.INDIVIDUAL),
    ENTREPRENEUR(PayerType.BUSINESS),
    COMPANY(PayerType.BUSINESS);

    private final PayerType payerType;

    UserType(PayerType payerType) {
        this.payerType = payerType;
    }

    public PayerType toPayerType() {
        return payerType;
    }
}
