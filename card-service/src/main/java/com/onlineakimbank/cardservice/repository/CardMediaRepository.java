package com.onlineakimbank.cardservice.repository;

import com.onlineakimbank.cardservice.entity.passive.CardMedia;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardMediaRepository extends CassandraRepository<CardMedia, String> {
    CardMedia findByCardId(String cardId);
}
