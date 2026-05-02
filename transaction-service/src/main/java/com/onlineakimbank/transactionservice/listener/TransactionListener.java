package com.onlineakimbank.transactionservice.listener;

import com.onlineakimbank.transactionservice.entity.Transaction;
import com.onlineakimbank.transactionservice.entity.enums.State;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import com.onlineakimbank.transactionservice.event.account.AccountStateChangeEvent;
import com.onlineakimbank.transactionservice.event.transaction.TransactionEvent;
import com.onlineakimbank.transactionservice.event.user.UserRegistrationEvent;
import com.onlineakimbank.transactionservice.event.user.UserUpdateEvent;
import com.onlineakimbank.transactionservice.repository.TransactionCassandraRepository;
import com.onlineakimbank.transactionservice.repository.TransactionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionListener {

    private final TransactionCassandraRepository cassandraRepository;
    private final TransactionRedisRepository redisRepository;


    @KafkaListener(
            topics = "transaction-topic",
            groupId = "transaction-service",
            containerFactory = "kafkaListenerContainerFactoryTransaction"
    )
    public void processTransaction(TransactionEvent event) {

        Transaction tx = cassandraRepository.findById(event.getTransactionId())
                .orElseThrow(() -> new IllegalStateException("[Transaction not found: " + event.getTransactionId() + "]"));

        boolean senderInvalid =
                tx.getSenderAccountState() == State.BLOCKED ||
                        tx.getSenderAccountState() == State.CLOSED ||
                        tx.getSenderAccountState() == State.FROZEN;

        boolean recipientInvalid =
                tx.getRecipientAccountState() == State.BLOCKED ||
                        tx.getRecipientAccountState() == State.CLOSED;

        if (senderInvalid || recipientInvalid) {
            redisRepository.updateStatus(tx.getTransactionId(), TransactionStatus.FAILED);
            log.warn("[Transaction {} failed: sender or recipient account is invalid | sender={}, recipient={}]",
                    tx.getTransactionId(), tx.getSenderAccountState(), tx.getRecipientAccountState());
            return;
        }

        redisRepository.updateStatus(tx.getTransactionId(), TransactionStatus.PROCESSING);
        log.info("[Transaction {} processing started]", tx.getTransactionId());

        tx.setTransactionStatus(TransactionStatus.COMPLETED);
        cassandraRepository.save(tx);

        redisRepository.updateStatus(tx.getTransactionId(), TransactionStatus.COMPLETED);
        log.info("[Transaction {} completed successfully]", tx.getTransactionId());
    }

    @KafkaListener(
            topics = "user-registration",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryUserRegistration"
    )
    public void processUserRegistration(UserRegistrationEvent event) {
        log.info("Received user registration event: {}", event.getUserId());
    }

    @KafkaListener(
            topics = "user-update",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryUserUpdate"
    )
    public void processUserUpdate(UserUpdateEvent event) {
        log.info("Received user update event: {}", event.getUserId());
    }
}
