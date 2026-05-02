package com.onlineakimbank.paymentservice.config;

import com.onlineakimbank.paymentservice.entity.enums.BusinessCashbackCategory;
import com.onlineakimbank.paymentservice.entity.enums.IndividualCashbackCategory;
import com.onlineakimbank.paymentservice.entity.enums.Status;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class BusinessCashbackConfig {

    private final Map<BusinessCashbackCategory, BigDecimal> nonPrimePercentMap = Map.ofEntries(
            Map.entry(BusinessCashbackCategory.OFFICE_SUPPLIES, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.TRAVEL, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.ENTERTAINMENT, new BigDecimal("1")),
            Map.entry(BusinessCashbackCategory.MARKETPLACES, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.FUEL, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.IT_SERVICES, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.NET, new BigDecimal("1")),
            Map.entry(BusinessCashbackCategory.TRAINING, new BigDecimal("1")),
            Map.entry(BusinessCashbackCategory.HEALTH, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.CATERING, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.OTHER, new BigDecimal("1"))
    );

    private final Map<BusinessCashbackCategory, BigDecimal> primePercentMap = Map.ofEntries(
            Map.entry(BusinessCashbackCategory.OFFICE_SUPPLIES, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.TRAVEL, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.ENTERTAINMENT, new BigDecimal("5")),
            Map.entry(BusinessCashbackCategory.MARKETPLACES, new BigDecimal("7")),
            Map.entry(BusinessCashbackCategory.FUEL, new BigDecimal("8")),
            Map.entry(BusinessCashbackCategory.IT_SERVICES, new BigDecimal("5")),
            Map.entry(BusinessCashbackCategory.NET, new BigDecimal("2")),
            Map.entry(BusinessCashbackCategory.TRAINING, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.HEALTH, new BigDecimal("3")),
            Map.entry(BusinessCashbackCategory.CATERING, new BigDecimal("5")),
            Map.entry(BusinessCashbackCategory.OTHER, new BigDecimal("3"))
    );

    private static final BigDecimal LIMIT_NON_PRIME = new BigDecimal("40000");
    private static final BigDecimal LIMIT_PRIME = new BigDecimal("150000");

    private Set<BusinessCashbackCategory> activeCategories;

    public BigDecimal resolvePercent(BusinessCashbackCategory category, Status status) {
        if (activeCategories == null || !activeCategories.contains(category)) return BigDecimal.ZERO; // неактивная категория
        return status == Status.PRIME
                ? primePercentMap.getOrDefault(category, BigDecimal.ZERO)
                : nonPrimePercentMap.getOrDefault(category, BigDecimal.ZERO);
    }

    public BigDecimal resolveLimit(Status status) {
        return status == Status.PRIME ? LIMIT_PRIME : LIMIT_NON_PRIME;
    }

    public Set<BusinessCashbackCategory> getActiveCategories() {
        return activeCategories;
    }

    public void validateSelectedCategories(List<BusinessCashbackCategory> selected, Status status) {
        int maxCategories = (status == Status.PRIME) ? 5 : 3;
        if (selected.size() > maxCategories) {
            throw new IllegalStateException("[Max " + maxCategories + " categories allowed for status " + status + "]");
        }
    }
}
