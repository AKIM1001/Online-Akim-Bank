package com.onlineakimbank.accountservice.repository;

import com.onlineakimbank.accountservice.entity.AccountLimit;
import org.springframework.stereotype.Repository;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountLimitCassandraRepository extends CassandraRepository<AccountLimit, String> {
    List<AccountLimit> findByAccountIdAndCurrencyAndDate(String accountId, String currency, LocalDate date);

    List<AccountLimit> findByAccountIdAndCurrencyAndDateBetween(String accountId, String currency, LocalDate start, LocalDate end);
}
