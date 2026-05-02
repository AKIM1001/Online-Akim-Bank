package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.dto.LoanCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.main.LoanCard;
import com.onlineakimbank.cardservice.entity.passive.LoanCardBalance;
import com.onlineakimbank.cardservice.entity.enums.AccountStatus;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import com.onlineakimbank.cardservice.exception.EntityNotFoundException;
import com.onlineakimbank.cardservice.mapper.LoanCardMapper;
import com.onlineakimbank.cardservice.repository.CardCassandraRepository;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.request.LoanCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanCardService extends AbstractService<LoanCardDto,
        LoanCard, LoanCardRequest> {

    private final CardJpaRepository cardRepository;
    private final CardCassandraRepository cardCassandraRepository;

    @Autowired
    public LoanCardService(CardJpaRepository cardRepository,
                           LoanCardMapper loanCardMapper,
                           CardCassandraRepository cardCassandraRepository) {
        super(cardRepository, loanCardMapper);
        this.cardRepository = cardRepository;
        this.cardCassandraRepository = cardCassandraRepository;
    }



    @Transactional
    @Override
    public LoanCardDto save(LoanCardRequest request) {

        LoanCard card = new LoanCard();
        card.setUserId(request.userId());
        card.setAccountId(request.accountId());
        card.setCardNetwork(request.cardNetwork());

        if (request.accountStatus() == AccountStatus.PRIME) {
            card.setLoanAmount(BigDecimal.valueOf(200_000));
            card.setNoInterestUntil(LocalDate.now().plusMonths(6));
            card.setDueDate(LocalDate.now().plusMonths(24));
        } else {
            card.setLoanAmount(BigDecimal.valueOf(50_000));
            card.setNoInterestUntil(LocalDate.now().plusMonths(2));
            card.setDueDate(LocalDate.now().plusMonths(12));
        }

        card.setRemainingDebt(BigDecimal.ZERO);
        card.setInterestRate(BigDecimal.valueOf(12));
        card.setCardStatus(CardStatus.ACTIVE);

        LoanCard saved = (LoanCard) cardRepository.save(card);

        LoanCardBalance balance = new LoanCardBalance(
                saved.getCardId(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                Instant.now()
        );
        cardCassandraRepository.save(balance);

        return mapper.toDto(saved);
    }

    @Transactional
    public void spendFromLoanCard(String cardId, BigDecimal amount) {

        LoanCard card = getLoanCard(cardId);

        if (card.getCardStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Card is not active");
        }

        LoanCardBalance balance = getBalance(cardId);

        BigDecimal debt = balance.getTotalSpent()
                .subtract(balance.getTotalPaid())
                .add(balance.getAccruedInterest());

        BigDecimal available = card.getLoanAmount().subtract(debt);

        if (amount.compareTo(available) > 0) {
            throw new IllegalArgumentException("Amount exceeds credit limit");
        }

        balance.setTotalSpent(balance.getTotalSpent().add(amount));
        balance.setLastUpdated(Instant.now());
        cardCassandraRepository.save(balance);
    }

    @Transactional
    public void payLoan(String cardId, BigDecimal amount) {

        LoanCardBalance balance = getBalance(cardId);

        balance.setTotalPaid(balance.getTotalPaid().add(amount));
        balance.setLastUpdated(Instant.now());
        cardCassandraRepository.save(balance);
    }

    private LoanCard getLoanCard(String cardId) {
        Card base = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("LoanCard not found"));
        if (!(base instanceof LoanCard card)) {
            throw new IllegalArgumentException("Card is not a LoanCard");
        }
        return card;
    }

    private LoanCardBalance getBalance(String cardId) {
        return cardCassandraRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found"));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void accrueInterest() {

        List<LoanCard> loanCards = cardRepository.findAll().stream()
                .filter(c -> c instanceof LoanCard)
                .map(c -> (LoanCard) c)
                .toList();

        Instant now = Instant.now();

        for (LoanCard card : loanCards) {

            LoanCardBalance balance = getBalance(card.getCardId());

            if (card.getNoInterestUntil().isBefore(LocalDate.now())) {
                BigDecimal debt = balance.getTotalSpent().subtract(balance.getTotalPaid());
                BigDecimal interest = debt.multiply(card.getInterestRate()).divide(BigDecimal.valueOf(100));
                balance.setAccruedInterest(balance.getAccruedInterest().add(interest));
            }

            if (card.getDueDate().isBefore(LocalDate.now()) &&
                    card.getCardStatus() == CardStatus.ACTIVE) {
                card.setCardStatus(CardStatus.FROZEN);
                card.setFrozenAt(now);
                cardRepository.save(card);
            }

            if (card.getCardStatus() == CardStatus.FROZEN &&
                    card.getFrozenAt().plus(90, ChronoUnit.DAYS).isBefore(now)) {
                card.setCardStatus(CardStatus.BLOCKED);
                card.setBlockedAt(now);
                cardRepository.save(card);
            }

            cardCassandraRepository.save(balance);
        }
    }
}


