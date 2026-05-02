package com.onlineakimbank.adminservice.service;

import com.onlineakimbank.adminservice.client.AccountClient;
import com.onlineakimbank.adminservice.client.CardClient;
import com.onlineakimbank.adminservice.dto.AccountDto;
import com.onlineakimbank.adminservice.dto.CardDto;
import com.onlineakimbank.adminservice.dto.enums.CardStatus;
import com.onlineakimbank.adminservice.dto.enums.State;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AccountClient accountClient;
    private final CardClient cardClient;

    public AdminService(AccountClient accountClient,
                        CardClient cardClient) {
        this.accountClient = accountClient;
        this.cardClient = cardClient;
    }

    public void freezeAccount(String accountId) {
        accountClient.changeState(accountId, State.FROZEN);
    }

    public void blockAccount(String accountId) {
        accountClient.changeState(accountId, State.BLOCKED);
    }

    public void closeAccount(String accountId) {
        accountClient.changeState(accountId, State.CLOSED);
    }

    public void activateAccount(String accountId) {
        accountClient.changeState(accountId, State.ACTIVE);
    }

    public void freezeCard(String cardId) {
        cardClient.changeStatus(cardId, CardStatus.FROZEN);
    }

    public void blockCard(String cardId) {
        cardClient.changeStatus(cardId, CardStatus.BLOCKED);
    }

    public void activateCard(String cardId) {
        cardClient.changeStatus(cardId, CardStatus.ACTIVE);
    }

    public List<AccountDto> getUserAccounts(String userId) {
        return accountClient.getAccountsByUser(userId);
    }

    public List<CardDto> getAccountCards(String accountId) {
        return cardClient.getCardsByAccount(accountId);
    }
}