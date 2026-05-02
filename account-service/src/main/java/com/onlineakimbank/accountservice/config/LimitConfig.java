package com.onlineakimbank.accountservice.config;

import com.onlineakimbank.accountservice.entity.enums.Currency;
import com.onlineakimbank.accountservice.entity.enums.Status;
import com.onlineakimbank.accountservice.service.CurrencyConverter;

import java.math.BigDecimal;

import com.onlineakimbank.accountservice.entity.enums.AccountType;

import static com.onlineakimbank.accountservice.entity.enums.AccountType.*;

public class LimitConfig {

    private static final BigDecimal INDIVIDUAL_DAILY_RUB = new BigDecimal("500000");
    private static final BigDecimal INDIVIDUAL_MONTHLY_RUB = new BigDecimal("10000000");

    private static final BigDecimal ENTREPRENEUR_DAILY_RUB = new BigDecimal("2000000");
    private static final BigDecimal ENTREPRENEUR_MONTHLY_RUB = new BigDecimal("50000000");

    private static final BigDecimal COMPANY_DAILY_RUB = new BigDecimal("5000000");
    private static final BigDecimal COMPANY_MONTHLY_RUB = new BigDecimal("500000000");

    public static Limit getLimit(AccountType accountType,
                                 Status status,
                                 Currency currency,
                                 CurrencyConverter converter) {
        BigDecimal dailyRUB, monthlyRUB;
        switch (accountType) {
            case INDIVIDUAL -> { dailyRUB = INDIVIDUAL_DAILY_RUB; monthlyRUB = INDIVIDUAL_MONTHLY_RUB; }
            case ENTREPRENEUR -> { dailyRUB = ENTREPRENEUR_DAILY_RUB; monthlyRUB = ENTREPRENEUR_MONTHLY_RUB; }
            case COMPANY -> { dailyRUB = COMPANY_DAILY_RUB; monthlyRUB = COMPANY_MONTHLY_RUB; }
            default -> throw new IllegalArgumentException("[Unknown client type]");
        }

        if (currency == Currency.RUB) return new Limit(dailyRUB, monthlyRUB);

        BigDecimal daily = converter.convert(dailyRUB, Currency.RUB, currency);
        BigDecimal monthly = converter.convert(monthlyRUB, Currency.RUB, currency);
        return new Limit(daily, monthly);
    }

    public static class Limit {
        private final BigDecimal daily;
        private final BigDecimal monthly;

        public Limit(BigDecimal daily, BigDecimal monthly) { this.daily = daily; this.monthly = monthly; }

        public BigDecimal getDaily() { return daily; }
        public BigDecimal getMonthly() { return monthly; }
    }
}

