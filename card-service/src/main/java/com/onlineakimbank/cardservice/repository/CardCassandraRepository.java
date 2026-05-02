package com.onlineakimbank.cardservice.repository;

import com.onlineakimbank.cardservice.entity.passive.LoanCardBalance;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardCassandraRepository extends CassandraRepository<LoanCardBalance, String> {
    Optional<LoanCardBalance> findByCardId(String cardId);

}
