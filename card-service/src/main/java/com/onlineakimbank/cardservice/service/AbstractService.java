package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import com.onlineakimbank.cardservice.entity.enums.UserType;
import com.onlineakimbank.cardservice.event.user.UserRegistrationEvent;
import com.onlineakimbank.cardservice.exception.EntityNotFoundException;
import com.onlineakimbank.cardservice.mapper.MapperContract;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class AbstractService<D, T, R>
        implements ServiceContract<D, Card, R> {

    protected final CardJpaRepository repository;
    protected final MapperContract<D, Card, R> mapper;

    @Override
    public D save(R r) {

        if (!(r instanceof UserRegistrationEvent event) || event.getUserType() != UserType.INDIVIDUAL) {
            throw new IllegalArgumentException("[Only INDIVIDUAL users can register a card]");
        }

        Card entity = mapper.fromRequest(r);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<D> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public D findByUserId(String userId) {
        return mapper.toDto(
                repository.findByUserId(userId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by userId=" + userId + "]"))
        );
    }

    @Override
    public D findByAccountId(String accountId) {
        return mapper.toDto(
                repository.findByAccountId(accountId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by accountId=" + accountId + "]"))
        );
    }

    @Override
    public D findByCardId(String cardId) {
        return mapper.toDto(
                repository.findByCardId(cardId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by cardId=" + cardId + "]"))
        );
    }

    @Override
    public D findByCardNumber(String cardNumber) {
        return mapper.toDto(
                repository.findByCardNumber(cardNumber)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by cardNumber=" + cardNumber + "]"))
        );
    }

    @Override
    public D findByEmail(String email) {
        return mapper.toDto(
                repository.findByEmail(email)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by email=" + email + "]"))
        );
    }

    @Override
    public D findByAccountNumber(String accountNumber) {
        return mapper.toDto(
                repository.findByAccountNumber(accountNumber)
                        .orElseThrow(() ->
                                new EntityNotFoundException("[Card not found by email=" + accountNumber + "]"))
        );
    }

    @Transactional
    @Override
    public void updateStatus(String cardId, CardStatus status) {

        Card card = repository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        card.setCardStatus(status);
        repository.save(card);
    }


    @Override
    public void deleteByUserId(String userId) {
        repository.findByUserId(userId).ifPresent(repository::delete);
    }

    @Override
    public void deleteByAccountId(String accountId) {
        repository.findByAccountId(accountId).ifPresent(repository::delete);
    }

    @Override
    public void deleteByCardId(String cardId) {
        repository.deleteById(cardId);
    }

    @Override
    public void deleteByCardNumber(String cardNumber) {
        repository.findByCardNumber(cardNumber).ifPresent(repository::delete);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.findByEmail(email).ifPresent(repository::delete);
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        repository.findByAccountNumber(accountNumber).ifPresent(repository::delete);
    }
}
