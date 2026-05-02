package com.onlineakimbank.loanservice.service;


import com.onlineakimbank.loanservice.exception.EntityNotFoundException;
import com.onlineakimbank.loanservice.mapper.MapperContract;
import com.onlineakimbank.loanservice.repository.LoanBaseJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractService<D, T, R>
        implements ServiceContract<D, T, R> {

    protected final LoanBaseJpaRepository<T, String> repository;
    protected final MapperContract<D, T, R> mapper;

    @Override
    public List<D> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public D findByAccountId(String accountId) {
        T entity = repository.findByAccountId(accountId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "[Not found by accountId=" + accountId + "]"));

        return mapper.toDto(entity);
    }

    @Override
    public D findByAccountNumber(String accountNumber) {
        T entity = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "[Not found by accountNumber=" + accountNumber + "]"));

        return mapper.toDto(entity);
    }

    @Override
    public void deleteByAccountId(String accountId) {
        repository.deleteByAccountId(accountId);
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        repository.deleteByAccountNumber(accountNumber);
    }
}

