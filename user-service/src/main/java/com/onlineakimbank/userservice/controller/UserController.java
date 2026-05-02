package com.onlineakimbank.userservice.controller;

import com.onlineakimbank.userservice.dto.UserDto;
import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.exception.NoSuchUserException;
import com.onlineakimbank.userservice.request.UserRequest;
import com.onlineakimbank.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUserWithRole(@RequestBody UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUserWithRole(userRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String userId,
            @RequestBody UserRequest userRequest,
            @RequestHeader("X-User-Role") Role currentUserRole // передаём роль текущего пользователя
    ) {
        UserDto updatedUser = userService.updateUser(userId, userRequest, currentUserRole);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/find/userId/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String userId) throws NoSuchUserException {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable("email") String email) throws NoSuchUserException {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/find/all")
    public Page<UserDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @DeleteMapping("/delete/userId/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) throws NoSuchUserException {
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable("email") String email) throws NoSuchUserException {
        userService.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/{userId}/add/{accountId}")
    public ResponseEntity<Void> addAccountToUser(
            @PathVariable String userId,
            @PathVariable String accountId
    ) {
        userService.addAccountToUser(userId, accountId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/account/{userId}/{accountId}/state")
    public ResponseEntity<Void> updateAccountState(
            @PathVariable String userId,
            @PathVariable String accountId,
            @RequestParam String state
    ) {
        userService.updateAccountState(userId, accountId, state);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/account/{userId}/{accountId}/status")
    public ResponseEntity<Void> updateAccountStatus(
            @PathVariable String userId,
            @PathVariable String accountId,
            @RequestParam String status
    ) {
        userService.updateAccountStatus(userId, accountId, status);
        return ResponseEntity.ok().build();
    }
}
