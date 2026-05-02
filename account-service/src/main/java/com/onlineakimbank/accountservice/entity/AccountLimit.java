package com.onlineakimbank.accountservice.entity;

import com.onlineakimbank.accountservice.entity.enums.Currency;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table("account_limits")
public class AccountLimit {

    @PrimaryKey
    private AccountLimitKey key;

    private String accountId;
    private Currency currency;
    private LocalDate date;
    private BigDecimal usedToday;
    private BigDecimal usedThisMonth;

    private String alias;

    private Integer dailyLimit;

    public AccountLimit() {}

    public AccountLimit(AccountLimitKey key,
                        BigDecimal usedToday,
                        BigDecimal usedThisMonth) {
        this.key = key;
        this.usedToday = usedToday;
        this.usedThisMonth = usedThisMonth;
    }

    public AccountLimitKey getKey() {
        return key;
    }

    public void setKey(AccountLimitKey key) {
        this.key = key;
    }

    public BigDecimal getUsedToday() {
        return usedToday;
    }

    public void setUsedToday(BigDecimal usedToday) {
        this.usedToday = usedToday;
    }

    public BigDecimal getUsedThisMonth() {
        return usedThisMonth;
    }

    public void setUsedThisMonth(BigDecimal usedThisMonth) {
        this.usedThisMonth = usedThisMonth;
    }
}



