package com.onlineakimbank.cardservice.repository;

import com.onlineakimbank.cardservice.entity.passive.ChildCard;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildCardCassandraRepository extends CassandraRepository<ChildCard, String> {

    Optional<ChildCard> findByCardId(String cardId);

}
