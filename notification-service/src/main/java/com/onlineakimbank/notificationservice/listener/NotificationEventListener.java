package com.onlineakimbank.notificationservice.listener;

import com.onlineakimbank.notificationservice.event.*;
import com.onlineakimbank.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user-registration", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryUserRegistration")
    public void handleUserRegistration(UserRegistrationEvent event) {
        log.info("[User registered: userId={}, email={}]", event.getUserId(), event.getEmail());
        notificationService.sendEmail(event.getEmail(), "[Welcome]", "[Thank you for registration, " + event.getFirstName() + "!]");
    }

    @KafkaListener(topics = "user-update", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryUserUpdate")
    public void handleUserUpdate(UserUpdateEvent event) {
        log.info("[User updated: userId={}]", event.getUserId());
        notificationService.sendEmail(event.getEmail(), "[Profile Updated]", "[Your profile has been updated.]");
    }

    @KafkaListener(topics = "user-remove", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryUserRemove")
    public void handleUserRemove(UserRemoveEvent event) {
        log.info("[User removed: userId={}]", event.getUserId());
        notificationService.sendEmail(null, "[Account Removed]", "[Your account has been removed.]");
    }

    @KafkaListener(topics = "account-registration", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryAccountRegistration")
    public void handleAccountRegistration(AccountRegistrationEvent event) {
        log.info("[Account registered: accountId={}, userId={}]", event.getAccountId(), event.getUserId());
        notificationService.sendEmail(null, "[Account Created]", "[Your account " + event.getAccountId() + " has been created.]");
    }

    @KafkaListener(topics = "account-remove", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryAccountRemove")
    public void handleAccountRemove(AccountRemoveEvent event) {
        log.info("[Account removed: accountId={}, userId={}]", event.getAccountId(), event.getUserId());
        notificationService.sendEmail(null, "[Account Removed]", "[Your account " + event.getAccountId() + " has been removed.]");
    }

    @KafkaListener(topics = "account-state-change", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryAccountStateChange")
    public void handleAccountStateChange(AccountStateChangeEvent event) {
        log.info("[Account state changed: accountId={}, state={}]", event.getAccountId(), event.getState());
        notificationService.sendEmail(null, "[Account Status Changed]", "[Your account status changed to: " + event.getState() + "]");
    }

    @KafkaListener(topics = "card-registration", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryCardRegistration")
    public void handleCardRegistration(CardRegistrationEvent event) {
        log.info("[Card registered: cardId={}, userId={}]", event.getCardId(), event.getUserId());
        notificationService.sendEmail(null, "[Card Created]", "[Your card " + event.getCardNumber() + " has been created.]");
    }

    @KafkaListener(topics = "card-status-change", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryCardStatusChange")
    public void handleCardStatusChange(CardStatusChangeEvent event) {
        log.info("[Card status changed: cardId={}, status={}]", event.getCardId(), event.getCardStatus());
        notificationService.sendEmail(null, "[Card Status Changed]", "[Your card status changed to: " + event.getCardStatus() + "]");
    }

    @KafkaListener(topics = "card-remove", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryCardRemove")
    public void handleCardRemove(CardRemoveEvent event) {
        log.info("[Card removed: cardId={}, userId={}]", event.getCardId(), event.getUserId());
        notificationService.sendEmail(null, "[Card Removed]", "[Your card " + event.getCardId() + " has been removed.]");
    }

    @KafkaListener(topics = "payment", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryDebitAccountCommand")
    public void handlePayment(DebitAccountCommand event) {
        log.info("[Payment processed: paymentId={}, accountId={}]", event.getPaymentId(), event.getAccountId());
        notificationService.sendSms(null, "[Payment of " + event.getAmount() + " " + event.getCurrency() + " processed.]");
    }

    @KafkaListener(topics = "transaction-topic", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryTransactionEvent")
    public void handleTransaction(TransactionEvent event) {
        log.info("[Transaction event: transactionId={}]", event.getTransactionId());
        notificationService.sendEmail(null, "[Transaction Update]", "[Transaction " + event.getTransactionId() + " completed.]");
    }

    @KafkaListener(topics = "loan-application", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryLoanApplication")
    public void handleLoanApplication(LoanApplicationEvent event) {
        log.info("[Loan application: applicationId={}, accountId={}]", event.getApplicationId(), event.getAccountId());
        notificationService.sendEmail(null, "[Loan Application]", "[Your loan application " + event.getApplicationId() + " has been submitted.]");
    }

    @KafkaListener(topics = "loan-decision", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactoryLoanDecision")
    public void handleLoanDecision(LoanDecisionEvent event) {
        log.info("[Loan decision: applicationId={}, status={}]", event.getApplicationId(), event.getStatus());
        notificationService.sendEmail(null, "[Loan Decision]", "[Your loan application " + event.getApplicationId() + " is " + event.getStatus() + "]");
    }
}