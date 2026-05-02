package com.onlineakimbank.paymentservice.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Set;

@Table("cashback_account")
@Data
public class CashbackAccount {

    @PrimaryKey
    private String accountId;

    private BigDecimal balance;

    private Set<String> individualCategories;

    private Set<String> businessCategories;
}
