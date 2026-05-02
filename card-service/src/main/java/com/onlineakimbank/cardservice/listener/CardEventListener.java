package com.onlineakimbank.cardservice.listener;

import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.enums.AccountState;
import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import com.onlineakimbank.cardservice.event.account.AccountStateChangeEvent;
import com.onlineakimbank.cardservice.event.card.CardRegistrationEvent;
import com.onlineakimbank.cardservice.event.card.CardRemoveEvent;
import com.onlineakimbank.cardservice.event.card.CardStatusChangeEvent;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.request.LoanCardRequest;
import com.onlineakimbank.cardservice.request.RealCardRequest;
import com.onlineakimbank.cardservice.service.ChildCardService;
import com.onlineakimbank.cardservice.service.LoanCardService;
import com.onlineakimbank.cardservice.service.RealCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardEventListener {

    private final CardJpaRepository cardRepository;
    private final RealCardService realCardService;
    private final ChildCardService childCardService;
    private final LoanCardService loanCardService;

    @KafkaListener(
            topics = "account-state-change",
            groupId = "card-service",
            containerFactory = "kafkaListenerContainerFactoryAccountStateChange"
    )
    public void onAccountStateChange(AccountStateChangeEvent event) {
        log.info("[CardService] Got account-state-change event: accountId={}, newState={}]",
                event.getAccountId(), event.getAccountState());

        Optional<Card> optionalCard = cardRepository.findByAccountId(event.getAccountId());
        if (optionalCard.isEmpty()) {
            log.info("[No card found for accountId={}]", event.getAccountId());
            return;
        }

        Card card = optionalCard.get();
        CardStatus newCardStatus = mapAccountStateToCardStatus(event.getAccountState());

        if (card.getCardStatus() != newCardStatus) {
            card.setCardStatus(newCardStatus);
            cardRepository.save(card);
            log.info("[Card {} status updated to {}]", card.getCardNumber(), newCardStatus);
        }
    }

    private CardStatus mapAccountStateToCardStatus(AccountState accountState) {
        return switch (accountState) {
            case ACTIVE -> CardStatus.ACTIVE;
            case BLOCKED -> CardStatus.BLOCKED;
            case FROZEN -> CardStatus.FROZEN;
            case CLOSED -> CardStatus.EXPIRED;
        };
    }

    @KafkaListener(
            topics = "card-registration",
            groupId = "card-service",
            containerFactory = "kafkaListenerContainerFactoryCardRegistration"
    )
    public void onCardRegistration(CardRegistrationEvent event) {

        log.info("[CardService] Card registration event: {}", event);

        RealCardRequest request = new RealCardRequest(
                event.getUserId(),
                event.getAccountId(),
                event.getCardNetwork()
        );

        realCardService.save(request);
    }


    @KafkaListener(
            topics = "card-status-change",
            groupId = "card-service",
            containerFactory = "kafkaListenerContainerFactoryCardStatusChange"
    )
    public void onCardStatusChange(CardStatusChangeEvent event) {

        log.info("[CardService] Card status change: cardId={}, status={}",
                event.getCardId(), event.getStatus());

        CardStatus newStatus = event.getStatus();

        realCardService.updateStatus(event.getCardId(), newStatus);
        childCardService.updateStatus(event.getCardId(), newStatus);
        loanCardService.updateStatus(event.getCardId(), newStatus);
    }

    @KafkaListener(
            topics = "card-remove",
            groupId = "card-service",
            containerFactory = "kafkaListenerContainerFactoryCardRemove"
    )
    public void onCardRemove(CardRemoveEvent event) {

        log.info("[CardService] Card remove event: cardId={}", event.getCardId());

        realCardService.deleteByCardId(event.getCardId());
        childCardService.deleteByCardId(event.getCardId());
        loanCardService.deleteByCardId(event.getCardId());
    }
}


