package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.entity.enums.CardStatus;
import com.onlineakimbank.cardservice.exception.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ServiceContract<D, T, R> {
    D save(R r) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

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

    void updateStatus(String cardId, CardStatus status) throws EntityNotFoundException;
    void deleteByUserId(String userId) throws EntityNotFoundException;
    void deleteByAccountId(String accountId) throws EntityNotFoundException;
    void deleteByAccountNumber(String accountNumber) throws EntityNotFoundException;
    void deleteByCardId(String cardId) throws EntityNotFoundException;
    void deleteByCardNumber(String cardNumber) throws EntityNotFoundException;
    void deleteByEmail(String email) throws EntityNotFoundException;
}
