package com.onlineakimbank.adminservice.listener;

import com.onlineakimbank.adminservice.event.*;
import com.onlineakimbank.adminservice.event.UserRegistrationEvent;
import com.onlineakimbank.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminEventListener {

    private final AdminService documentService;

    @KafkaListener(
            topics = "user-registration",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactoryUserRegistration"
    )
    public void handleUserRegistration(UserRegistrationEvent event) {
        System.out.println("[User created: " + event.getUserId() + "]");
    }

    @KafkaListener(
            topics = "user-update",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactoryUserUpdate"
    )
    public void handleUserUpdate(UserUpdateEvent event) {
        System.out.println("[User updated: " + event.getUserId() + "]");
    }

    @KafkaListener(
            topics = "account-registration",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountRegistration"
    )
    public void handleAccountRegistration(AccountRegistrationEvent event) {
        System.out.println("[Account created: " + event.getUserId() + "]");
    }

    @KafkaListener(
            topics = "account-remove",
            groupId = "account-service",
            containerFactory = "kafkaListenerContainerFactoryAccountRemove"
    )
    public void handleAccountRemove(AccountRemoveEvent event) {
        System.out.println("[Account removed: " + event.getUserId() + "]");
    }

    @KafkaListener(
            topics = "loan-application",
            groupId = "loan-service",
            containerFactory = "kafkaListenerContainerFactoryLoanApplication"
    )
    public void handleLoanApplication(LoanApplicationEvent event) {
        System.out.println("[Loan application: " + event.getUserId() + "]");
    }
}
