package com.onlineakimbank.loanservice.service;

import com.onlineakimbank.loanservice.exception.EntityNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ServiceContract<D, T, R> {
    D save(R r) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    List<D> findAll();

    D findByAccountId(String accountId) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByAccountNumber(String accountNumber) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    void deleteByAccountId(String accountId) throws EntityNotFoundException;
    void deleteByAccountNumber(String accountNumber) throws EntityNotFoundException;
}