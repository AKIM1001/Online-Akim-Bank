package com.onlineakimbank.paymentservice.config;

import com.onlineakimbank.paymentservice.entity.enums.Currency;
import com.onlineakimbank.paymentservice.entity.enums.IndividualCashbackCategory;
import com.onlineakimbank.paymentservice.entity.enums.Status;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Configuration
public class IndividualCashbackConfig {

    private static final int MAX_ACTIVE_CATEGORIES = 5;

    private static final BigDecimal LIMIT_NON_PRIME = new BigDecimal("5000");
    private static final BigDecimal LIMIT_PRIME = new BigDecimal("15000");

    private static final Map<IndividualCashbackCategory, BigDecimal> NON_PRIME_PERCENT = Map.ofEntries(
            Map.entry(IndividualCashbackCategory.SUPERMARKETS, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.FUEL, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.RESTAURANTS, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.PHARMACY, new BigDecimal("1")),
            Map.entry(IndividualCashbackCategory.TAXI, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.TRAVEL, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.ENTERTAINMENT, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.MARKETPLACES, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.ELECTRONICS, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.CLOTHING, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.BEAUTY, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.SPORT, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.CHILDREN, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.PETS, new BigDecimal("1")),
            Map.entry(IndividualCashbackCategory.EDUCATION, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.OTHER, new BigDecimal("1"))
    );

    private static final Map<IndividualCashbackCategory, BigDecimal> PRIME_PERCENT = Map.ofEntries(
            Map.entry(IndividualCashbackCategory.SUPERMARKETS, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.FUEL, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.RESTAURANTS, new BigDecimal("7")),
            Map.entry(IndividualCashbackCategory.PHARMACY, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.TAXI, new BigDecimal("4")),
            Map.entry(IndividualCashbackCategory.TRAVEL, new BigDecimal("8")),
            Map.entry(IndividualCashbackCategory.ENTERTAINMENT, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.MARKETPLACES, new BigDecimal("6")),
            Map.entry(IndividualCashbackCategory.ELECTRONICS, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.CLOTHING, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.BEAUTY, new BigDecimal("4")),
            Map.entry(IndividualCashbackCategory.SPORT, new BigDecimal("7")),
            Map.entry(IndividualCashbackCategory.CHILDREN, new BigDecimal("5")),
            Map.entry(IndividualCashbackCategory.PETS, new BigDecimal("2")),
            Map.entry(IndividualCashbackCategory.EDUCATION, new BigDecimal("3")),
            Map.entry(IndividualCashbackCategory.OTHER, new BigDecimal("3"))
    );

    public BigDecimal resolvePercent(IndividualCashbackCategory category, Status status, Currency currency) {
        if (currency != Currency.RUB) {
            return BigDecimal.ZERO;
        }

        return status == Status.PRIME
                ? PRIME_PERCENT.getOrDefault(category, BigDecimal.ZERO)
                : NON_PRIME_PERCENT.getOrDefault(category, BigDecimal.ZERO);
    }

    public BigDecimal resolveLimit(Status status) {
        return status == Status.PRIME ? LIMIT_PRIME : LIMIT_NON_PRIME;
    }

    public void validateSelectedCategories(List<IndividualCashbackCategory> selected, Status status) {
        int maxCategories = (status == Status.PRIME) ? 5 : 3;
        if (selected.size() > maxCategories) {
            throw new IllegalStateException("[Max " + maxCategories + " categories allowed for status " + status + "]");
        }
    }
}