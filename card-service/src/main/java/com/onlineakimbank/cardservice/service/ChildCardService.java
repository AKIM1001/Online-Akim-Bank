package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.dto.ChildCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.passive.ChildCard;
import com.onlineakimbank.cardservice.exception.EntityNotFoundException;
import com.onlineakimbank.cardservice.mapper.ChildCardMapper;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.repository.ChildCardCassandraRepository;
import com.onlineakimbank.cardservice.request.ChildCardRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChildCardService extends AbstractService<ChildCardDto, ChildCard, ChildCardRequest> {
    @Autowired
    public ChildCardService(CardJpaRepository cardJpaRepository,
                            ChildCardMapper childCardMapper, ChildCardCassandraRepository cardCassandraRepository, CardJpaRepository cardRepository) {
        super(cardJpaRepository, childCardMapper);
        this.cardCassandraRepository = cardCassandraRepository;
        this.cardRepository = cardRepository;
    }

    private final ChildCardCassandraRepository cardCassandraRepository;
    private final CardJpaRepository cardRepository;

    @Transactional
    public void allocateLimitToChild(
            String parentCardId,
            String childCardId,
            BigDecimal limit
    ) {

        if (limit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("[Limit must be positive]");
        }

        Card parent = cardRepository.findByCardId(parentCardId)
                .orElseThrow(() -> new EntityNotFoundException("[Parent card not found]"));

        Card card = cardRepository.findByCardId(childCardId)
                .orElseThrow(() -> new EntityNotFoundException("[Child card not found]"));

        if (!(card instanceof ChildCard child)) {
            throw new IllegalArgumentException("[Card is not a ChildCard]");
        }

        if (!child.getParentCardId().equals(parentCardId)) {
            throw new IllegalArgumentException("[This child card does not belong to parent]");
        }

        if (parent.getBalance().compareTo(limit) < 0) {
            throw new IllegalArgumentException("[Not enough money on parent card]");
        }

        child.setSpendingLimit(limit);
        child.setSpentAmount(BigDecimal.ZERO);

        cardCassandraRepository.save(child);
    }

    @Transactional
    public void spendFromChildCard(String childCardId, BigDecimal amount) {
        ChildCard child = (ChildCard) cardCassandraRepository.findByCardId(childCardId)
                .orElseThrow(() -> new EntityNotFoundException("[Child card not found]"));

        BigDecimal remaining = child.getSpendingLimit().subtract(child.getSpentAmount());
        if (amount.compareTo(remaining) > 0) {
            throw new IllegalArgumentException("[Exceeds remaining limit on child card]");
        }

        child.setSpentAmount(child.getSpentAmount().add(amount));
        cardCassandraRepository.save(child);

    }

}

