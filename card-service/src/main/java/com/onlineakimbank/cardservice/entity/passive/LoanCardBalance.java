package com.onlineakimbank.cardservice.entity.passive;

import com.onlineakimbank.cardservice.entity.main.LoanCard;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_card_balance")
public class LoanCardBalance {

    @PrimaryKey
    private String cardId;

    private BigDecimal totalSpent;
    private BigDecimal totalPaid;
    private BigDecimal accruedInterest;
    private Instant lastUpdated;

}

