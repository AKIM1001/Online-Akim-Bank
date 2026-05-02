package com.onlineakimbank.transactionservice.entity;


import com.onlineakimbank.transactionservice.entity.enums.*;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Table("transaction")
public class Transaction {

    @PrimaryKey
    private String transactionId;
    
    @Column("transaction_number")
    private String transactionNumber;

    @Column("account_id")
    private String accountId;

    @Column("sender_account_id")
    private String senderAccountId;

    @Column("recipient_account_id")
    private String recipientAccountId;

    @Column("sender_account_number")
    private String senderAccountNumber;

    @Column("recipient_account_number")
    private String recipientAccountNumber;

    @Column("sender_account_state")
    private State senderAccountState;

    @Column("recipient_account_state")
    private State recipientAccountState;

    @Column("sender_card_number")
    private String senderCardNumber;

    @Column("recipient_card_number")
    private String recipientCardNumber;

    @Column("sender_balance_before")
    private BigDecimal senderBalanceBefore;

    @Column("sender_balance_after")
    private BigDecimal senderBalanceAfter;

    @Column("recipient_balance_before")
    private BigDecimal recipientBalanceBefore;

    @Column("recipient_balance_after")
    private BigDecimal recipientBalanceAfter;

    @Column("recipient_card_balance_before")
    private BigDecimal recipientCardBalanceBefore;

    @Column("recipient_card_balance_after")
    private BigDecimal recipientCardBalanceAfter;

    @Column("transaction_method")
    private TransactionMethod transactionMethod;

    @Column("transaction_status")
    private TransactionStatus transactionStatus;

    @Column("user_type")
    private UserType userType;

    @Column("currency")
    private Currency currency;

    @Column("amount")
    private BigDecimal amount;

    @Column("payment_purpose")
    private String paymentPurpose;

    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    @Column("executed_at")
    private Instant executedAt;

    public void touch() {
        this.updatedAt = Instant.now();
    }

}
