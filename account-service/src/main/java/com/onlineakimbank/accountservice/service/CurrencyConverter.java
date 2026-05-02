package com.onlineakimbank.accountservice.service;

import com.onlineakimbank.accountservice.entity.enums.Currency;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal convert(BigDecimal amount, Currency from, Currency to);
    BigDecimal getRate(Currency from, Currency to);
}
