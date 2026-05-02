package com.onlineakimbank.paymentservice.repository;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.Payment;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentRedisRepository {

    private final RedisTemplate<String, Payment> redisTemplate;
    private static final String PAYMENT_KEY = "payment:";

    public PaymentDto save(Payment payment) {
        String key = PAYMENT_KEY + payment.getPaymentId();

        Instant now = Instant.now();
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(now);
            payment.setPaymentStatus(PaymentStatus.CREATED);
        }
        payment.setUpdatedAt(now);

        redisTemplate.opsForValue().set(key, payment);

        return toDto(payment);
    }

    public void updateStatus(String paymentId, PaymentStatus newStatus) {

        String key = PAYMENT_KEY + paymentId;

        Payment payment = redisTemplate.opsForValue().get(key);

        if (payment == null) {
            throw new IllegalStateException("Payment not found: " + paymentId);
        }

        payment.setPaymentStatus(newStatus);

        redisTemplate.opsForValue().set(key, payment);
    }

    public List<PaymentDto> findAll() {
        return redisTemplate.keys(PAYMENT_KEY + "*").stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(p -> p != null)
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public Optional<PaymentDto> findByPaymentId(String paymentId) {
        String key = PAYMENT_KEY + paymentId;
        Payment payment = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(payment != null ? toDto(payment) : null);
    }

    public List<PaymentDto> findByAccountId(String accountId) {
        return redisTemplate.keys(PAYMENT_KEY + accountId + ":*").stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(p -> p != null)
                .map(this::toDto)
                .toList();
    }

    public List<PaymentDto> findByAccountNumber(String accountNumber) {
        return redisTemplate.keys(PAYMENT_KEY + accountNumber + ":*").stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(p -> p != null)
                .map(this::toDto)
                .toList();
    }

    public boolean deleteByPaymentId(String paymentId) {
        return Boolean.TRUE.equals(redisTemplate.delete(PAYMENT_KEY + paymentId));
    }

    public boolean deleteByAccountId(String accountId) {
        return Boolean.TRUE.equals(redisTemplate.delete(PAYMENT_KEY + accountId));
    }

    public boolean deleteByAccountNumber(String accountNumber) {
        return Boolean.TRUE.equals(redisTemplate.delete(PAYMENT_KEY + accountNumber));
    }

    public Optional<PaymentDto> markAsProcessingAndDebit(String paymentId, BigDecimal amountToDebit) {
        String key = PAYMENT_KEY + paymentId;

        PaymentDto result = redisTemplate.execute((connection) -> {
            Payment current = redisTemplate.opsForValue().get(key);
            if (current == null) return null;

            if (current.getBalanceBefore() == null || current.getBalanceBefore().compareTo(amountToDebit) < 0) {
                return null;
            }

            current.setBalanceAfter(current.getBalanceBefore().subtract(amountToDebit));
            current.setPaymentStatus(PaymentStatus.PROCESSING);
            current.setUpdatedAt(Instant.now());
            redisTemplate.opsForValue().set(key, current);
            return toDto(current);

        }, true);

        return Optional.ofNullable(result);
    }
    public PaymentDto markAsCompleted(String paymentId) {
        String key = PAYMENT_KEY + paymentId;
        Payment payment = redisTemplate.opsForValue().get(key);
        if (payment == null) throw new IllegalStateException("Payment not found");

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setExecutedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());

        redisTemplate.opsForValue().set(key, payment);
        return toDto(payment);
    }

    public PaymentDto markAsFailed(String paymentId, String reason) {
        String key = PAYMENT_KEY + paymentId;
        Payment payment = redisTemplate.opsForValue().get(key);
        if (payment == null) throw new IllegalStateException("Payment not found");

        payment.setPaymentStatus(PaymentStatus.FAILED);
        payment.setExecutedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());

        redisTemplate.opsForValue().set(key, payment);
        return toDto(payment);
    }

    private PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getAccountId(),
                null,
                payment.getPayerType(),
                payment.getPaymentStatus(),
                payment.getPaymentMethod(),
                payment.getPaymentType(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getCreatedAt(),
                payment.getUpdatedAt(),
                payment.getExecutedAt()
        );
    }


}

