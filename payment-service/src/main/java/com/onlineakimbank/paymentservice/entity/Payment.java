package com.onlineakimbank.paymentservice.entity;

import com.onlineakimbank.paymentservice.entity.enums.*;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Data
@Table("payment")
public class Payment {


    @PrimaryKey
    private String paymentId;


    @Column("account_id")
    private String accountId;

    @Column("payer_type")
    private PayerType payerType;

    @Column("payment_type")
    private PaymentType paymentType;

    @Column("payment_method")
    private PaymentMethod paymentMethod;

    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("state")
    private State state;

    @Column("amount")
    private BigDecimal amount;

    @Column("currency")
    private Currency currency;

    @Column("balance_before")
    private BigDecimal balanceBefore;

    @Column("balance_after")
    private BigDecimal balanceAfter;

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