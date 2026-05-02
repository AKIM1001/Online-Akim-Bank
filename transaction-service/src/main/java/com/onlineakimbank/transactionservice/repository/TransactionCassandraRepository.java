package com.onlineakimbank.transactionservice.repository;

import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.entity.Transaction;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface TransactionCassandraRepository
        extends CassandraRepository<Transaction, String> {

    List<Transaction> findAllByAccountId(String accountId);
    List<Transaction> findAllByTransactionNumber(String transactionNumber);
    List<Transaction> findAll();

    default List<TransactionDto> findByTransactionNumber(String transactionNumber) {
        return findAllByTransactionNumber(transactionNumber).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<TransactionDto> findByAccountId(String accountId) {
        return findAllByAccountId(accountId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default Optional<TransactionDto> findByTransactionId(String transactionId) {
        return findById(transactionId).map(this::toDto);
    }

    default List<TransactionDto> findAllDto() {
        return findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    void deleteByTransactionId(String transactionId);

    default void deleteByTransactionNumber(String transactionNumber) {
        findAllByTransactionNumber(transactionNumber).forEach(this::delete);
    }

    default Optional<TransactionDto> findDtoById(String id) {
        return findById(id).map(this::toDto);
    }

    default TransactionDto toDto(Transaction tx) {
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
