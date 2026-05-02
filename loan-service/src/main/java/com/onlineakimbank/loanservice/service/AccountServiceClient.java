package com.onlineakimbank.loanservice.service;

import com.onlineakimbank.loanservice.entity.enums.Currency;
import java.math.BigDecimal;

public class AccountServiceClient {

    public BigDecimal getExchangeRate(Currency from, Currency to) {
        if (from == to) return BigDecimal.ONE;

        throw new UnsupportedOperationException(
                "[Exchange rate must be fetched from AccountService for " + from + " -> " + to + "]"
        );
    }

    public BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        BigDecimal rate = getExchangeRate(from, to);
        return amount.multiply(rate);
    }
}