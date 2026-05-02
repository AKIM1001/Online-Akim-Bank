package com.onlineakimbank.accountservice.service;

import com.onlineakimbank.accountservice.config.AccountCurrencyConfig;
import com.onlineakimbank.accountservice.config.LimitConfig;
import com.onlineakimbank.accountservice.entity.Account;
import com.onlineakimbank.accountservice.entity.AccountLimit;
import com.onlineakimbank.accountservice.entity.enums.Currency;
import com.onlineakimbank.accountservice.repository.AccountLimitCassandraRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final AccountLimitCassandraRepository limitRepo;
    private final CurrencyConverter converter;

    public TransactionService(AccountLimitCassandraRepository limitRepo, CurrencyConverter converter) {
        this.limitRepo = limitRepo;
        this.converter = converter;
    }

    public void validateTransaction(Account account, BigDecimal amount, Currency currency) {
        if (!AccountCurrencyConfig.isCurrencyAllowed(account.getAccountType(), account.getStatus(), currency)) {
            throw new IllegalArgumentException("[Currency not available for account: " + currency + "]");
        }

        LimitConfig.Limit limit = LimitConfig.getLimit(
                account.getAccountType(),
                account.getStatus(),
                currency,
                converter
        );

        BigDecimal usedToday = getUsedToday(account.getAccountId(), currency);
        BigDecimal usedMonth = getUsedThisMonth(account.getAccountId(), currency);

        if (usedToday.add(amount).compareTo(limit.getDaily()) > 0) {
            throw new IllegalStateException("[Daily limit for currency exceeded " + currency + "]");
        }

        if (usedMonth.add(amount).compareTo(limit.getMonthly()) > 0) {
            throw new IllegalStateException("[Monthly currency limit exceeded " + currency + "]");
        }
    }

    private BigDecimal getUsedToday(String accountId, Currency currency) {
        LocalDate today = LocalDate.now();
        List<AccountLimit> limits = limitRepo.findByAccountIdAndCurrencyAndDate(accountId, currency.name(), today);
        return limits.stream()
                .map(AccountLimit::getUsedToday)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getUsedThisMonth(String accountId, Currency currency) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        List<AccountLimit> limits = limitRepo.findByAccountIdAndCurrencyAndDateBetween(accountId, currency.name(), startOfMonth, today);
        return limits.stream()
                .map(AccountLimit::getUsedThisMonth)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
