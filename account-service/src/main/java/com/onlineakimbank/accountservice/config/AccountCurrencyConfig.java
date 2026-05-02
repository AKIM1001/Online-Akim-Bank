package com.onlineakimbank.accountservice.config;

import com.onlineakimbank.accountservice.entity.enums.AccountType;
import com.onlineakimbank.accountservice.entity.enums.Currency;
import com.onlineakimbank.accountservice.entity.enums.Status;

import java.util.*;

import static com.onlineakimbank.accountservice.entity.enums.Currency.RUB;

public class AccountCurrencyConfig {

    private static final Map<AccountType, Map<Status, Set<Currency>>> currencyMap = new EnumMap<>(AccountType.class);

    static {
        currencyMap.put(AccountType.INDIVIDUAL, Map.of(
                Status.NON_PRIME, Set.of(RUB),
                Status.PRIME, Set.of(RUB, Currency.USD, Currency.EUR, Currency.CNY, Currency.BYN, Currency.KZT, Currency.AMD)
        ));

        currencyMap.put(AccountType.ENTREPRENEUR, Map.of(
                Status.NON_PRIME, Set.of(RUB, Currency.USD, Currency.EUR),
                Status.PRIME, Set.of(RUB, Currency.USD, Currency.EUR, Currency.CNY, Currency.BYN, Currency.KZT, Currency.AMD, Currency.UZS, Currency.AED, Currency.INR)
        ));

        currencyMap.put(AccountType.COMPANY, Map.of(
                Status.NON_PRIME, Set.of(RUB, Currency.USD, Currency.EUR, Currency.CNY, Currency.BYN, Currency.KZT, Currency.AMD),
                Status.PRIME, EnumSet.allOf(Currency.class)
        ));
    }

    public static boolean isCurrencyAllowed(AccountType type, Status status, Currency currency) {
        Set<Currency> cur = currencyMap.get(type).get(status);
        return cur != null && cur.contains(currency);
    }
}
