package com.onlineakimbank.loanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanBaseJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findByAccountId(String accountId);

    Optional<T> findByAccountNumber(String accountNumber);

    void deleteByAccountId(String accountId);

    void deleteByAccountNumber(String accountNumber);
}
