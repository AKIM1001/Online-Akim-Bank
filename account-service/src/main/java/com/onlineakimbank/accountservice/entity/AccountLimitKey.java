package com.onlineakimbank.accountservice.entity;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.*;

import java.io.Serializable;
import java.time.LocalDate;

@PrimaryKeyClass
public class AccountLimitKey implements Serializable {

    @PrimaryKeyColumn(name = "account_id", type = PARTITIONED)
    private String accountId;

    @PrimaryKeyColumn(name = "currency", type = PARTITIONED)
    private String currency;

    @PrimaryKeyColumn(name = "date", type = CLUSTERED)
    private LocalDate date;

}

