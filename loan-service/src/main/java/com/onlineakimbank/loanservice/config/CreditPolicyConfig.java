package com.onlineakimbank.loanservice.config;

import com.onlineakimbank.loanservice.service.AccountServiceClient;
import com.onlineakimbank.loanservice.entity.enums.CollateralType;
import com.onlineakimbank.loanservice.entity.enums.Currency;
import com.onlineakimbank.loanservice.entity.enums.UserType;

import java.math.BigDecimal;
import java.util.Set;

public class CreditPolicyConfig {

    private static final BigDecimal INDIVIDUAL_MAX_RUB = new BigDecimal("5000000");
    private static final BigDecimal ENTREPRENEUR_MAX_RUB = new BigDecimal("20000000");
    private static final BigDecimal COMPANY_MAX_RUB = new BigDecimal("200000000");

    private static final int INDIVIDUAL_MAX_TERM = 60;
    private static final int ENTREPRENEUR_MAX_TERM = 84;
    private static final int COMPANY_MAX_TERM = 120;

    private static final BigDecimal INDIVIDUAL_MARGIN = new BigDecimal("4");
    private static final BigDecimal ENTREPRENEUR_MARGIN = new BigDecimal("3");
    private static final BigDecimal COMPANY_MARGIN = new BigDecimal("2");

    private static final BigDecimal PRIME_DISCOUNT = new BigDecimal("1.5");

    public static CreditPolicy getPolicy(UserType userType,
                                         Currency currency,
                                         boolean prime,
                                         AccountServiceClient converter,
                                         BigDecimal cbRate) {

        BigDecimal maxRub;
        int maxTerm;
        BigDecimal margin;
        Set<CollateralType> collateral;
        Set<Currency> allowedCurrencies;

        switch (userType) {

            case INDIVIDUAL -> {
                maxRub = INDIVIDUAL_MAX_RUB;
                maxTerm = INDIVIDUAL_MAX_TERM;
                margin = INDIVIDUAL_MARGIN;

                collateral = Set.of(
                        CollateralType.CAR,
                        CollateralType.REAL_ESTATE
                );

                allowedCurrencies = Set.of(Currency.RUB);
            }

            case ENTREPRENEUR -> {
                maxRub = ENTREPRENEUR_MAX_RUB;
                maxTerm = ENTREPRENEUR_MAX_TERM;
                margin = ENTREPRENEUR_MARGIN;

                collateral = Set.of(
                        CollateralType.CAR,
                        CollateralType.REAL_ESTATE,
                        CollateralType.EQUIPMENT
                );

                allowedCurrencies = Set.of(
                        Currency.RUB, Currency.USD, Currency.EUR, Currency.CNY, Currency.GBP
                );
            }

            case COMPANY -> {
                maxRub = COMPANY_MAX_RUB;
                maxTerm = COMPANY_MAX_TERM;
                margin = COMPANY_MARGIN;

                collateral = Set.of(
                        CollateralType.REAL_ESTATE,
                        CollateralType.EQUIPMENT,
                        CollateralType.SECURITIES
                );

                allowedCurrencies = Set.of(
                        Currency.RUB, Currency.USD, Currency.EUR, Currency.GBP, Currency.CHF, Currency.CAD, Currency.JPY
                );
            }

            default -> throw new IllegalArgumentException("[Unknown client type]");
        }

        if (!allowedCurrencies.contains(currency))
            throw new IllegalArgumentException("[Currency not allowed for this client type]");

        if (prime) margin = margin.subtract(PRIME_DISCOUNT);

        BigDecimal interest = cbRate.add(margin);

        BigDecimal maxAmount;

        if (currency == Currency.RUB) {
            maxAmount = maxRub;
        } else {
            maxAmount = converter.convert(maxRub, Currency.RUB, currency);
        }



        return new CreditPolicy(
                maxAmount,
                maxTerm,
                interest,
                collateral,
                allowedCurrencies
        );
    }

    public static class CreditPolicy {

        private final BigDecimal maxAmount;
        private final int maxTermMonths;
        private final BigDecimal interestRate;
        private final Set<CollateralType> allowedCollateral;
        private final Set<Currency> allowedCurrencies;

        public CreditPolicy(BigDecimal maxAmount,
                            int maxTermMonths,
                            BigDecimal interestRate,
                            Set<CollateralType> allowedCollateral,
                            Set<Currency> allowedCurrencies) {

            this.maxAmount = maxAmount;
            this.maxTermMonths = maxTermMonths;
            this.interestRate = interestRate;
            this.allowedCollateral = allowedCollateral;
            this.allowedCurrencies = allowedCurrencies;
        }

        public BigDecimal getMaxAmount() { return maxAmount; }

        public int getMaxTermMonths() { return maxTermMonths; }

        public BigDecimal getInterestRate() { return interestRate; }

        public Set<CollateralType> getAllowedCollateral() { return allowedCollateral; }

        public Set<Currency> getAllowedCurrencies() { return allowedCurrencies; }
    }
}