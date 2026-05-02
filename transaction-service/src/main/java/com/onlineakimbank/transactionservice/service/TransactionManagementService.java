package com.onlineakimbank.transactionservice.service;

import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import com.onlineakimbank.transactionservice.repository.TransactionCassandraRepository;
import com.onlineakimbank.transactionservice.repository.TransactionRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionManagementService {

    private final TransactionRedisRepository redisRepository;
    private final TransactionCassandraRepository cassandraRepository;

    public TransactionDto findByTransactionId(String transactionId) {
        return redisRepository.findByTransactionId(transactionId)
                .or(() -> cassandraRepository.findByTransactionId(transactionId))
                .orElseThrow(() -> new IllegalStateException("[Transaction not found: " + transactionId + "]"));
    }

    public List<TransactionDto> findByTransactionNumber(String transactionNumber) {
        List<TransactionDto> all = new ArrayList<>();
        all.addAll(cassandraRepository.findByTransactionNumber(transactionNumber));
        return all;
    }

    public void deleteByTransactionNumber(String transactionNumber) {
        cassandraRepository.deleteByTransactionNumber(transactionNumber);
    }

    public void updateStatus(String paymentId, TransactionStatus status) {
        redisRepository.updateStatus(paymentId, status);
    }

    public Optional<TransactionDto> markAsProcessingAndDebit(String transactionId, BigDecimal amountToDebit) {
        return redisRepository.markAsProcessingAndDebit(transactionId, amountToDebit);
    }
}
