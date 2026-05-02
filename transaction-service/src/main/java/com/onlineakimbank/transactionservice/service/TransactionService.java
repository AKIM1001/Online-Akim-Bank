package com.onlineakimbank.transactionservice.service;

import com.onlineakimbank.transactionservice.client.AccountClient;
import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.entity.Transaction;
import com.onlineakimbank.transactionservice.entity.enums.Currency;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import com.onlineakimbank.transactionservice.event.transaction.TransactionEvent;
import com.onlineakimbank.transactionservice.repository.TransactionCassandraRepository;
import com.onlineakimbank.transactionservice.repository.TransactionRedisRepository;
import com.onlineakimbank.transactionservice.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRedisRepository redisRepository;
    private final TransactionCassandraRepository cassandraRepository;
    private final AccountClient accountClient;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionDto createTransaction(TransactionRequest request) {

        BigDecimal senderBalance =
                accountClient.getBalance(request.senderAccountId());

        BigDecimal recipientBalance =
                accountClient.getBalance(request.recipientAccountId());

        if (senderBalance.compareTo(request.amount()) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        BigDecimal senderAfter =
                senderBalance.subtract(request.amount());

        BigDecimal recipientAfter =
                recipientBalance.add(request.amount());

        Transaction tx = new Transaction();

        tx.setTransactionId(UUID.randomUUID().toString());
        tx.setTransactionNumber(UUID.randomUUID().toString());

        tx.setAccountId(request.accountId());

        tx.setSenderAccountId(request.senderAccountId());
        tx.setRecipientAccountId(request.recipientAccountId());

        tx.setSenderCardNumber(request.senderCardNumber());
        tx.setRecipientCardNumber(request.recipientCardNumber());

        tx.setAmount(request.amount());

        tx.setCurrency(
                Currency.valueOf(
                        request.currency().getCurrencyCode()
                )
        );

        tx.setPaymentPurpose(request.paymentPurpose());

        tx.setUserType(request.userType());
        tx.setTransactionMethod(request.transactionMethod());

        tx.setTransactionStatus(TransactionStatus.CREATED);

        tx.setSenderBalanceBefore(senderBalance);
        tx.setRecipientBalanceBefore(recipientBalance);

        tx.setSenderBalanceAfter(senderAfter);
        tx.setRecipientBalanceAfter(recipientAfter);

        TransactionDto saved = redisRepository.save(tx);

        TransactionEvent event = new TransactionEvent();

        event.setCorrelationId(UUID.randomUUID().toString());
        event.setTransactionId(saved.transactionId());

        event.setAccountId(saved.senderAccountId());

        event.setAmount(saved.amount());

        event.setCurrency(
                Currency.valueOf(
                        saved.currency().getCurrencyCode()
                )
        );

        event.setHoldFunds(false);

        event.setMetadata(saved.recipientAccountId());

        kafkaTemplate.send(
                "transaction-topic",
                saved.transactionId(),
                event
        );

        redisRepository.updateStatus(
                saved.transactionId(),
                TransactionStatus.SENT
        );

        return saved;
    }

    public void markProcessing(String transactionId) {
        redisRepository.updateStatus(transactionId, TransactionStatus.PROCESSING);
    }

    public void finalizeTransaction(String transactionId, boolean success) {

        TransactionDto dto = redisRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalStateException("[Transaction not found in Redis]"));

        if (success) {
            redisRepository.updateStatus(transactionId, TransactionStatus.COMPLETED);
        } else {
            redisRepository.updateStatus(transactionId, TransactionStatus.FAILED);
        }

        Transaction entity = mapToEntity(dto);

        cassandraRepository.save(entity);

        redisRepository.deleteByTransactionId(transactionId);
    }

    private Transaction mapToEntity(TransactionDto dto) {

        Transaction tx = new Transaction();

        tx.setTransactionId(dto.transactionId());
        tx.setAccountId(dto.accountId());
        tx.setAmount(dto.amount());
        tx.setCurrency(com.onlineakimbank.transactionservice.entity.enums.Currency.valueOf(dto.currency().getCurrencyCode()));
        tx.setTransactionStatus(dto.transactionStatus());
        tx.setCreatedAt(dto.createdAt());
        tx.setUpdatedAt(dto.updatedAt());
        tx.setExecutedAt(dto.executedAt());

        return tx;
    }
}
