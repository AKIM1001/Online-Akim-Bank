package com.onlineakimbank.paymentservice.repository;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.CashbackAccount;
import com.onlineakimbank.paymentservice.entity.Payment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface PaymentCassandraRepository extends CassandraRepository<Payment, String> {

    List<Payment> findAllByAccountId(String accountId);
    List<Payment> findAllByAccountNumber(String accountNumber);
    List<Payment> findAll();

    default List<PaymentDto> findByAccountId(String accountId) {
        return findAllByAccountId(accountId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<PaymentDto> findByAccountNumber(String accountNumber) {
        return findAllByAccountNumber(accountNumber).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default Optional<PaymentDto> findByPaymentId(String paymentId) {
        return findById(paymentId).map(this::toDto);
    }

    default List<PaymentDto> findAllDto() {
        return findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    void deleteByPaymentId(String paymentId);

    default void deleteByAccountId(String accountId) {
        findAllByAccountId(accountId).forEach(this::delete);
    }

    default void deleteByAccountNumber(String accountNumber) {
        findAllByAccountNumber(accountNumber).forEach(this::delete);
    }

    default PaymentDto toDto(Payment payment) {
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

    @Query("UPDATE cashback_account SET individual_categories = ?1 WHERE accountid = ?0")
    void saveIndividualCategories(String accountId, Set<String> categories);

    @Query("UPDATE cashback_account SET business_categories = ?1 WHERE accountid = ?0")
    void saveBusinessCategories(String accountId, Set<String> categories);

    @Query("SELECT individual_categories FROM cashback_account WHERE accountid = ?0")
    Optional<CashbackAccount> getIndividualCategories(String accountId);

    @Query("SELECT business_categories FROM cashback_account WHERE accountid = ?0")
    Optional<CashbackAccount> getBusinessCategories(String accountId);

    @Query("UPDATE cashback_account SET balance = balance + ?1 WHERE accountid = ?0")
    void addCashback(String accountId, BigDecimal amount);

    @Query("SELECT balance FROM cashback_account WHERE accountid = ?0")
    Optional<CashbackAccount> getBalance(String accountId);
}