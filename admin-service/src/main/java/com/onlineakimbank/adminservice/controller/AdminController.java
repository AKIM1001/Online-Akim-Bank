package com.onlineakimbank.adminservice.controller;

import com.onlineakimbank.adminservice.dto.AccountDto;
import com.onlineakimbank.adminservice.dto.CardDto;
import com.onlineakimbank.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/accounts/{accountId}/freeze")
    public void freezeAccount(@PathVariable String accountId) {
        adminService.freezeAccount(accountId);
    }

    @PatchMapping("/accounts/{accountId}/block")
    public void blockAccount(@PathVariable String accountId) {
        adminService.blockAccount(accountId);
    }

    @PatchMapping("/accounts/{accountId}/close")
    public void closeAccount(@PathVariable String accountId) {
        adminService.closeAccount(accountId);
    }

    @PatchMapping("/accounts/{accountId}/activate")
    public void activateAccount(@PathVariable String accountId) {
        adminService.activateAccount(accountId);
    }

    @PatchMapping("/cards/{cardId}/freeze")
    public void freezeCard(@PathVariable String cardId) {
        adminService.freezeCard(cardId);
    }

    @PatchMapping("/cards/{cardId}/block")
    public void blockCard(@PathVariable String cardId) {
        adminService.blockCard(cardId);
    }

    @PatchMapping("/cards/{cardId}/activate")
    public void activateCard(@PathVariable String cardId) {
        adminService.activateCard(cardId);
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountDto> getUserAccounts(@PathVariable String userId) {
        return adminService.getUserAccounts(userId);
    }

    @GetMapping("/accounts/{accountId}/cards")
    public List<CardDto> getAccountCards(@PathVariable String accountId) {
        return adminService.getAccountCards(accountId);
    }
}