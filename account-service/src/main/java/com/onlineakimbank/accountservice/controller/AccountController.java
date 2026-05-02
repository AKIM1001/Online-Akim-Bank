package com.onlineakimbank.accountservice.controller;

import com.onlineakimbank.accountservice.dto.AccountDto;
import com.onlineakimbank.accountservice.exception.NoSuchAccountException;
import com.onlineakimbank.accountservice.request.AccountRequest;
import com.onlineakimbank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountDto> registerAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.registerAccount(accountRequest));
    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.updateAccount(accountRequest));
    }

    @PutMapping("/update/state/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccountState(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.updateState(accountRequest));
    }

    @PutMapping("/update/status/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccountStatus(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.updateStatus(accountRequest));
    }

    @GetMapping("/find/account-number/{account-number}")
    public ResponseEntity<AccountDto> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) throws NoSuchAccountException {
        return ResponseEntity.ok(accountService.findByAccountNumber(accountNumber));
    }

    @GetMapping("/find/user/{userId}")
    public ResponseEntity<List<AccountDto>> findByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    @GetMapping("/find/user/{accountId}")
    public ResponseEntity<List<AccountDto>> findByAccountId(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(accountService.findByAccountId(accountId));
    }

    @DeleteMapping("/delete/account-number/{accountNumber}")
    public ResponseEntity<Void> deleteByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        accountService.deleteByAccountNumber(accountNumber);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable("userId") String userId) {
        accountService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/user/{accountId}")
    public ResponseEntity<Void> deleteByAccountId(@PathVariable("accountId") String accountId) {
        accountService.deleteByAccountId(accountId);
        return ResponseEntity.ok().build();
    }
}
