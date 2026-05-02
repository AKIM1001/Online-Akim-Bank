package com.onlineakimbank.paymentservice.service;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import com.onlineakimbank.paymentservice.repository.PaymentRedisRepository;
import com.onlineakimbank.paymentservice.repository.PaymentCassandraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentManagementService {

    private final PaymentRedisRepository redisRepository;
    private final PaymentCassandraRepository cassandraRepository;

    public PaymentDto findByPaymentId(String paymentId) {
        return redisRepository.findByPaymentId(paymentId)
                .or(() -> cassandraRepository.findByPaymentId(paymentId))
                .orElseThrow(() -> new IllegalStateException("[Payment not found: " + paymentId + "]"));
    }

    public List<PaymentDto> findByAccountId(String accountId) {
        List<PaymentDto> all = new ArrayList<>();
        all.addAll(redisRepository.findByAccountId(accountId));
        all.addAll(cassandraRepository.findByAccountId(accountId));
        return all;
    }

    public List<PaymentDto> findByAccountNumber(String accountNumber) {
        List<PaymentDto> all = new ArrayList<>();
        all.addAll(redisRepository.findByAccountNumber(accountNumber));
        all.addAll(cassandraRepository.findByAccountNumber(accountNumber));
        return all;
    }

    public List<PaymentDto> findAll() {
        List<PaymentDto> all = new ArrayList<>();

        all.addAll(redisRepository.findAll());

        all.addAll(
                cassandraRepository.findAll().stream()
                        .map(cassandraRepository::toDto)
                        .collect(Collectors.toList())
        );

        return all;
    }

    public void deleteByPaymentId(String paymentId) {
        redisRepository.deleteByPaymentId(paymentId);
        cassandraRepository.deleteByPaymentId(paymentId);
    }

    public void deleteByAccountId(String accountId) {
        redisRepository.deleteByAccountId(accountId);
        cassandraRepository.deleteByAccountId(accountId);
    }

    public void deleteByAccountNumber(String accountNumber) {
        redisRepository.deleteByAccountNumber(accountNumber);
        cassandraRepository.deleteByAccountNumber(accountNumber);
    }

    public void updateStatus(String paymentId, PaymentStatus status) {
        redisRepository.updateStatus(paymentId, status);
    }

    public Optional<PaymentDto> markAsProcessingAndDebit(String paymentId, BigDecimal amountToDebit) {
        return redisRepository.markAsProcessingAndDebit(paymentId, amountToDebit);
    }
}