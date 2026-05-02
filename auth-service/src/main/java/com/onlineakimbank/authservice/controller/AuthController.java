package com.onlineakimbank.authservice.controller;

import com.onlineakimbank.authservice.event.UserLoginEvent;
import com.onlineakimbank.authservice.event.UserRegistrationEvent;
import com.onlineakimbank.authservice.event.UserRemoveEvent;
import com.onlineakimbank.authservice.listener.AuthEventListener;
import com.onlineakimbank.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthEventListener userEventListener;
    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> signUp(@RequestBody UserRegistrationEvent event) {
        event.setCorrelationId(UUID.randomUUID().toString());
        userEventListener.handleUserRegistration(event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserLoginEvent event) {
        event.setCorrelationId(UUID.randomUUID().toString());
        return ResponseEntity.ok(userEventListener.handleUserLogin(event));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal Jwt jwt) {
        authService.logoutUser(jwt.getSubject());
        return ResponseEntity.ok("[User successfully logged out from all sessions.]");
    }

    @PostMapping("/remove-user")
    public ResponseEntity<?> removeUser(@RequestBody UserRemoveEvent event) {
        event.setCorrelationId(UUID.randomUUID().toString());
        userEventListener.handleUserRemove(event);
        return ResponseEntity.ok("[User with ID: " + event.getUserId() + "successfully removed!]");
    }
}
