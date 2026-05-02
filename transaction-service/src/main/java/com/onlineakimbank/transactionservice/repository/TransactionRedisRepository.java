package com.onlineakimbank.transactionservice.repository;

import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.entity.Transaction;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.onlineakimbank.transactionservice.entity.enums.Currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TransactionRedisRepository {

    private final RedisTemplate<String, Transaction> redisTemplate;

    private static final String KEY_PREFIX = "transaction:";

    public TransactionDto save(Transaction tx) {

        String key = KEY_PREFIX + tx.getTransactionId();

        Instant now = Instant.now();

        if (tx.getCreatedAt() == null) {
            tx.setCreatedAt(now);
            tx.setTransactionStatus(TransactionStatus.CREATED);
        }

        tx.setUpdatedAt(now);

        redisTemplate.opsForValue().set(key, tx);

        return toDto(tx);
    }

    public List<TransactionDto> findAll() {
        return redisTemplate.keys(KEY_PREFIX + "*").stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(p -> p != null)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDto> findByTransactionId(String transactionId) {

        Transaction tx = redisTemplate.opsForValue()
                .get(KEY_PREFIX + transactionId);

        return Optional.ofNullable(tx)
                .map(this::toDto);
    }

    public void updateStatus(String transactionId, TransactionStatus status) {

        String key = KEY_PREFIX + transactionId;

        Transaction tx = redisTemplate.opsForValue().get(key);

        if (tx == null) {
            throw new IllegalStateException("[Transaction not found]");
        }

        tx.setTransactionStatus(TransactionStatus.CREATED);
        tx.setUpdatedAt(Instant.now());

        redisTemplate.opsForValue().set(key, tx);
    }

    public boolean deleteByTransactionId(String transactionId) {
        return Boolean.TRUE.equals(
                redisTemplate.delete(KEY_PREFIX + transactionId)
        );
    }

    public Optional<TransactionDto> markAsProcessingAndDebit(String transactionId, BigDecimal amountToDebit) {
        String key = KEY_PREFIX + transactionId;
        Transaction tx = redisTemplate.opsForValue().get(key);
        if (tx == null) return Optional.empty();

        if (tx.getSenderBalanceAfter() == null) {
            tx.setSenderBalanceAfter(tx.getSenderBalanceBefore());
        }

        tx.setSenderBalanceAfter(tx.getSenderBalanceAfter().subtract(amountToDebit));

        tx.setTransactionStatus(TransactionStatus.PROCESSING);
        tx.setUpdatedAt(Instant.now());

        redisTemplate.opsForValue().set(key, tx);
        return Optional.of(toDto(tx));
    }

    private TransactionDto toDto(Transaction tx) {
        return new TransactionDto(
                tx.getTransactionId(),
                tx.getTransactionNumber(),
                tx.getAccountId(),
                tx.getSenderAccountId(),
                tx.getRecipientAccountId(),
                tx.getSenderAccountNumber(),
                tx.getRecipientAccountNumber(),
                tx.getSenderCardNumber(),
                tx.getRecipientCardNumber(),
                tx.getSenderBalanceBefore(),
                tx.getSenderBalanceAfter(),
                tx.getRecipientBalanceBefore(),
                tx.getRecipientBalanceAfter(),
                tx.getRecipientCardBalanceBefore(),
                tx.getRecipientCardBalanceAfter(),
                tx.getTransactionMethod(),
                tx.getTransactionStatus(),
                tx.getUserType(),
                java.util.Currency.getInstance(tx.getCurrency().name()),
                tx.getAmount(),
                tx.getPaymentPurpose(),
                tx.getCreatedAt(),
                tx.getUpdatedAt(),
                tx.getExecutedAt()
        );
    }
}