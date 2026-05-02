package com.onlineakimbank.userservice.listener;

import com.onlineakimbank.userservice.event.UserRegisteredEvent;
import com.onlineakimbank.userservice.event.UserRegistrationEvent;
import com.onlineakimbank.userservice.event.UserRemoveEvent;
import com.onlineakimbank.userservice.event.UserRemovedEvent;
import com.onlineakimbank.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventListener {

    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;
    private final KafkaTemplate<String, UserRegistrationEvent> userRegistrationTemplate;
    private final KafkaTemplate<String, UserRemovedEvent> userRemovedTemplate;
    private final UserService userService;

    @KafkaListener(
            topics = "user-registration",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactoryUserRegistration"
    )
    public void listenerUserRegistration(UserRegistrationEvent event) {
        userService.registerUserWithRole(event);

        userRegisteredTemplate.send(
                "user-registered",
                new UserRegisteredEvent(
                        event.getCorrelationId(),
                        event.getUserId(),
                        userService.existsById(event.getUserId())
                )
        );
    }


    @KafkaListener(
            topics = "user-registration",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactoryUserRegistration"
    )
    public void handleUserRegistration(UserRegistrationEvent registrationEvent) {
        userService.registerUserWithRole(registrationEvent);

        UserRegisteredEvent registeredEvent = new UserRegisteredEvent(
                registrationEvent.getCorrelationId(),
                registrationEvent.getUserId(),
                userService.existsById(registrationEvent.getUserId())
        );

        userRegisteredTemplate.send("user-registered", registeredEvent);
    }


    @KafkaListener(
            topics = "user-remove",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactoryUserRemove"
    )
    public void listenerUserRemove(UserRemoveEvent event) {
        userService.deleteById(event.getUserId());

        userRemovedTemplate.send(
                "user-removed",
                new UserRemovedEvent(
                        event.getCorrelationId(),
                        event.getUserId(),
                        userService.existsById(event.getUserId())
                )
        );
    }
}
