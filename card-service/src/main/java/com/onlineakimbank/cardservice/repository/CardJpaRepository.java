package com.onlineakimbank.cardservice.repository;

import com.onlineakimbank.cardservice.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardJpaRepository extends BaseJpaRepository<Card> {

    Optional<Card> findByUserId(String userId);

    Optional<Card> findByAccountId(String accountId);

    Optional<Card> findByCardNumber(String cardNumber);

    Optional<Card> findByCardId(String cardId);

    Optional<Card> findByEmail(String email);

    Optional<Card> findByAccountNumber(String accountNumber);
}

