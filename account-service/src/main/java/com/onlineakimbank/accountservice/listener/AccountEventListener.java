package com.onlineakimbank.accountservice.listener;

import com.onlineakimbank.accountservice.entity.Account;
import com.onlineakimbank.accountservice.entity.enums.AccountType;
import com.onlineakimbank.accountservice.entity.enums.State;
import com.onlineakimbank.accountservice.event.DebitAccountCommand;
import com.onlineakimbank.accountservice.event.TransactionEvent;
import com.onlineakimbank.accountservice.event.account.AccountRegistrationEvent;
import com.onlineakimbank.accountservice.event.account.AccountRemoveEvent;
import com.onlineakimbank.accountservice.event.account.AccountStateChangeEvent;
import com.onlineakimbank.accountservice.event.account.AccountStatusChangeEvent;
import com.onlineakimbank.accountservice.repository.AccountJpaRepository;
import com.onlineakimbank.accountservice.request.AccountRequest;
import com.onlineakimbank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventListener {

    private final AccountJpaRepository accountRepository;
    private final KafkaTemplate<String, AccountRegistrationEvent> accountRegistrationTemplate;
    private final KafkaTemplate<String, AccountStateChangeEvent> accountStateChangeTemplate;
    private final KafkaTemplate<String, AccountStatusChangeEvent> accountStatusChangeTemplate;
    private final KafkaTemplate<String, AccountRemoveEvent> accountRemoveTemplate;

    private final AccountService accountService;

    @KafkaListener(
            topics = "account-registration",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountRegistration"
    )
    public void listenerAccountRegistration(AccountRegistrationEvent event) {

        AccountRequest request = new AccountRequest(
                event.getAccountId(),
                event.getUserId(),
                event.getBalance(),
                event.getAccountNumber(),
                event.getCurrency(),
                event.getState(),
                event.getStatus(),
                event.getUserRole(),
                AccountType.valueOf(event.getAccountType()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        accountService.registerAccount(request);

        accountRegistrationTemplate.send(
                "user-account-registered",
                event
        );
    }


    @KafkaListener(
            topics = "account-state-change",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountStateChange"
    )
    public void listenerAccountStateChange(AccountStateChangeEvent event) {

        AccountRequest request = new AccountRequest(
                event.getAccountId(),
                event.getUserId(),
                null,
                event.getAccountNumber(),
                null,
                event.getState(),
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        accountService.updateState(request);

        accountStateChangeTemplate.send(
                "user-account-state-changed",
                event
        );
    }



    @KafkaListener(
            topics = "account-status-change",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountStatusChange"
    )
    public void listenerAccountStatusChange(AccountStatusChangeEvent event) {

        AccountRequest request = new AccountRequest(
                event.getAccountId(),
                event.getUserId(),
                null,
                event.getAccountNumber(),
                null,
                null,
                event.getStatus(),
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        accountService.updateStatus(request);

        accountStatusChangeTemplate.send(
                "user-account-status-changed",
                event
        );
    }


    @KafkaListener(
            topics = "account-remove",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountRemove"
    )
    public void listenerAccountRemove(AccountRemoveEvent event) {

        AccountRequest request = new AccountRequest(
                event.getAccountId(),
                event.getUserId(),
                null,
                event.getAccountNumber(),
                null,
                null,
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        accountService.deleteByAccountId(event.getAccountId());

        accountRemoveTemplate.send(
                "user-account-removed",
                event
        );
    }

    @KafkaListener(topics = "transaction-topic")
    public void process(TransactionEvent event) {

        Account sender = accountRepository.findByAccountId(event.getSenderAccountId())
                .orElseThrow(() -> new IllegalStateException("Sender account not found"));

        Account recipient = accountRepository.findByAccountId(event.getRecipientAccountId())
                .orElseThrow(() -> new IllegalStateException("Recipient account not found"));

        sender.setBalance(event.getSenderBalanceAfter());
        recipient.setBalance(event.getRecipientBalanceAfter());

        accountRepository.save(sender);
        accountRepository.save(recipient);
    }

    @KafkaListener(topics = "payment-topic", groupId = "account-service")
    public void processPayment(DebitAccountCommand event) {

        Account account = accountRepository.findByAccountId(event.getAccountId())
                .orElseThrow(() -> new IllegalStateException("Account not found: " + event.getAccountId()));

        State accountState = account.getState();
        if (accountState == State.BLOCKED || accountState == State.CLOSED || accountState == State.FROZEN) {
            log.warn("Payment {} cannot be applied: account {} is {}", event.getPaymentId(), account.getAccountId(), accountState);
            return;
        }

        account.setBalance(event.getBalanceAfter());
        accountRepository.save(account);

        log.info("Payment {} applied: account {} balance updated to {}", event.getPaymentId(),
                account.getAccountId(), event.getBalanceAfter());
    }

}
