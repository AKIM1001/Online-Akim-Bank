package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.exception.EntityNotFoundException;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Controller<D, T, R> {

    ResponseEntity<D> save(R r) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    List<D> findAll();

    D findByUserId(String userId) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByAccountId(String accountId) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByCardId(String cardId) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByCardNumber(String cardNumber) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByEmail(String email) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    D findByAccountNumber(String accountNumber) throws EntityNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    void deleteByUserId(String userId) throws EntityNotFoundException;
    void deleteByAccountId(String accountId) throws EntityNotFoundException;
    void deleteByAccountNumber(String accountNumber) throws EntityNotFoundException;
    void deleteByCardId(String cardId) throws EntityNotFoundException;
    void deleteByCardNumber(String cardNumber) throws EntityNotFoundException;
    void deleteByEmail(String email) throws EntityNotFoundException;
}

