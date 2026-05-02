package com.onlineakimbank.accountservice.service;

import com.onlineakimbank.accountservice.dto.AccountDto;
import com.onlineakimbank.accountservice.entity.Account;
import com.onlineakimbank.accountservice.entity.enums.State;
import com.onlineakimbank.accountservice.exception.AccountAlreadyExistsException;
import com.onlineakimbank.accountservice.exception.NoSuchAccountException;
import com.onlineakimbank.accountservice.mapper.AccountMapper;
import com.onlineakimbank.accountservice.repository.AccountJpaRepository;
import com.onlineakimbank.accountservice.repository.AccountLimitCassandraRepository;
import com.onlineakimbank.accountservice.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountLimitCassandraRepository accountCassandraRepository;
    private final AccountMapper accountMapper;

    public AccountDto registerAccount(AccountRequest request) {
        if (accountJpaRepository.existsByAccountNumber(request.accountNumber())) {
            throw new AccountAlreadyExistsException("[Account with accountNumber " + request.accountNumber() + " already exists]");
        }
        Account account = accountMapper.requestToEntity(request);
        Account saved = accountJpaRepository.save(account);
        return accountMapper.entityToDto(saved);
    }

    public AccountDto findByAccountNumber(String accountNumber) {
        return accountJpaRepository.findById(accountNumber)
                .map(accountMapper::entityToDto)
                .orElseThrow(NoSuchAccountException::new);
    }

    public AccountDto updateAccount(AccountRequest request) {

        Account existing = accountJpaRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(NoSuchAccountException::new);

        Account saved = accountJpaRepository.save(existing);
        return accountMapper.entityToDto(saved);
    }

    public Page<AccountDto> findAll(Pageable pageable) {
        return accountJpaRepository.findAll(pageable).map(accountMapper::entityToDto);
    }

    public void checkFrozenTimeout(Account account) {

        if (account.getState() == State.FROZEN && account.getFrozenAt() != null) {
            long hours = ChronoUnit.HOURS.between(account.getFrozenAt(), LocalDateTime.now());

            if (hours >= 72) {
                account.setState(State.BLOCKED);
            }
        }
    }


    public AccountDto updateState(AccountRequest request) {

        Account account = accountJpaRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(NoSuchAccountException::new);

        checkFrozenTimeout(account);
        State newState = request.state();

        if (newState == State.FROZEN) {
            account.setFrozenAt(LocalDateTime.now());
        }

        account.setState(newState);
        Account saved = accountJpaRepository.save(account);
        return accountMapper.entityToDto(saved);
    }

    public AccountDto updateStatus(AccountRequest request) {

        Account account = accountJpaRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(NoSuchAccountException::new);

        account.setStatus(request.status());

        Account saved = accountJpaRepository.save(account);
        return accountMapper.entityToDto(saved);
    }

    public List<AccountDto> findByAccountId(String accountId) {
        return accountJpaRepository.findByAccountId(accountId)
                .stream()
                .map(accountMapper::entityToDto)
                .toList();
    }

    public List<AccountDto> findByUserId(String userId) {
        return accountJpaRepository.findByUserId(userId)
                .stream()
                .map(accountMapper::entityToDto)
                .toList();
    }

    public void deleteByAccountNumber(String accountNumber) {
        if (!accountJpaRepository.existsById(accountNumber)) throw new NoSuchAccountException();
        accountJpaRepository.deleteById(accountNumber);
    }

    public void deleteByUserId(String userId) {
        if (!accountJpaRepository.existsById(userId)) throw new NoSuchAccountException();
        accountJpaRepository.deleteById(userId);
    }

    public void deleteByAccountId(String accountId) {
        if (!accountJpaRepository.existsById(accountId)) throw new NoSuchAccountException();
        accountJpaRepository.deleteById(accountId);
    }

}
