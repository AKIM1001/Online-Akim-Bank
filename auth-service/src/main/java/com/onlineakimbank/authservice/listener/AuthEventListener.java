package com.onlineakimbank.authservice.listener;


import com.onlineakimbank.authservice.event.*;
import com.onlineakimbank.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthEventListener {

    private static final Logger log = Logger.getLogger(AuthEventListener.class.getName());
    private final KafkaTemplate<String, UserRegistrationEvent> userRegistrationTemplate;
    private final KafkaTemplate<String, UserRemoveEvent> userRemoveTemplate;
    private final AuthService authService;

    public void handleUserRegistration(UserRegistrationEvent userRegistrationEvent) {
        try{
            String KeycloakUserId = authService.registerUserWithRole(userRegistrationEvent.getFirstName(), userRegistrationEvent.getLastName(),
                    userRegistrationEvent.getEmail(), userRegistrationEvent.getRole(), userRegistrationEvent.getPassword());

            UserRegistrationEvent registrationEvent = new UserRegistrationEvent(
                    userRegistrationEvent.getCorrelationId(),
                    KeycloakUserId,
                    userRegistrationEvent.getFirstName(),
                    userRegistrationEvent.getLastName(),
                    userRegistrationEvent.getEmail(),
                    userRegistrationEvent.getRole(),
                    userRegistrationEvent.getPassword()
            );

            userRegistrationTemplate.send("userRegistration", registrationEvent);

            log.info("[User successfully registered! KeycloakID: " + KeycloakUserId + "]");
        } catch (Exception e) {
            log.severe("[User registration failed! Error: " + e.getMessage() + "]");
        }
    }

    @KafkaListener(topics = "user-registered", groupId = "user-service",
         containerFactory = "KafkaListenerContainerFactoryUserRegistered")
    public void finalizeRegistration(UserRegisteredEvent userRegisteredEvent) {
        if (userRegisteredEvent.isUserExists()) {
            log.info("[User successfully registered! KeycloakID: " + userRegisteredEvent.getUserId() + "]");
        }
    }

    public ResponseEntity<?> handleUserLogin(UserLoginEvent userLoginEvent) {
        try {
            AccessTokenResponse tokenResponse = authService.authenticateUser(userLoginEvent.getFirstName(),  userLoginEvent.getLastName(),
                    userLoginEvent.getEmail(), userLoginEvent.getPassword());
            log.info("[User login successful! Access token generated!]");
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            log.severe("[User login failed! Error: " + e.getMessage() + "]");
            ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(401).build();
    }

    public void handleUserRemove(UserRemoveEvent userRemoveEvent) {
        try {
            userRemoveTemplate.send("userRemove", userRemoveEvent);
            log.info("[User logged out successfully!]");
        } catch (Exception e) {
            log.severe("[User logged out failed! Error: " + e.getMessage() + "]");
        }
    }

    @KafkaListener(topics = "user-removed", groupId = "user-service",
             containerFactory = "KafkaListenerContainerFactoryUserRemoved")
    public ResponseEntity<?> finalizeRemove(UserRemovedEvent userRemovedEvent) {
        try {
            if (!userRemovedEvent.isUserExists()) {
                authService.removeUser(userRemovedEvent.getUserId());
                log.info("[User with ID: " + userRemovedEvent.getUserId() + " removed successfully!]");
            }
            return ResponseEntity.ok("[User removed successfully!]");
        } catch (Exception e) {
            log.severe("[User removed failed! Error: " + e.getMessage() + "]");
            return ResponseEntity.status(500).body("[User removed failed! Error: " + e.getMessage() + "]");
        }
    }
}
