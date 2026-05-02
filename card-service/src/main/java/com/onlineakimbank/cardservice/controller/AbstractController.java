package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.service.ServiceContract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractController<D, T, R> implements Controller<D, T, R> {
    
    private final ServiceContract<D, T, R> service;

    protected AbstractController(ServiceContract<D, T, R> service) { this.service = service; }

    @PostMapping("/register")
    @Override
    public ResponseEntity<D> save(@RequestBody R request) throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(request));
    }

    @GetMapping("/find/all")
    @Override
    public List<D> findAll() { return service.findAll(); }

    @GetMapping("/find/userId/{userId}")
    @Override
    public D findByUserId(@PathVariable String userId) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {

        return service.findByUserId(userId);
    }

    @GetMapping("/find/accountId/{accountId}")
    @Override
    public D findByAccountId(@PathVariable String accountId) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {

        return service.findByAccountId(accountId);
    }

    @GetMapping("/find/cardId/{cardId}")
    @Override
    public D findByCardId(@PathVariable String cardId) throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException {

        return service.findByCardId(cardId);
    }

    @GetMapping("/find/email/{email}")
    @Override
    public D findByEmail(@PathVariable String email) throws InvocationTargetException, NoSuchMethodException,
            IllegalAccessException {

        return service.findByEmail(email);
    }

    @GetMapping("/find/accountNumber/{accountNumber}")
    @Override
    public D findByAccountNumber(@PathVariable String accountNumber) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {

        return service.findByAccountNumber(accountNumber);
    }

    @GetMapping("/find/cardNumber/{cardNumber}")
    @Override
    public D findByCardNumber(@PathVariable String cardNumber) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {

        return service.findByCardNumber(cardNumber);
    }

    @DeleteMapping("/delete/userId/{userId}")
    @Override
    public void deleteByUserId(@PathVariable String userId) {
        service.deleteByUserId(userId);
    }

    @DeleteMapping("/delete/acccountId/{accountId}")
    @Override
    public void deleteByAccountId(@PathVariable String accountId) {
        service.deleteByAccountId(accountId);
    }

    @DeleteMapping("/delete/cardId/{cardId}")
    @Override
    public void deleteByCardId(@PathVariable String cardId) {
        service.deleteByCardId(cardId);
    }

    @DeleteMapping("/delete/email/{email}")
    @Override
    public void deleteByEmail(@PathVariable String email) {
        service.deleteByEmail(email);
    }

    @DeleteMapping("/delete/accountNumber/{accountNumber}")
    @Override
    public void deleteByAccountNumber(@PathVariable String accountNumber) {
        service.deleteByAccountNumber(accountNumber);
    }

    @DeleteMapping("/delete/cardNumber/{cardNumber}")
    @Override
    public void deleteByCardNumber(@PathVariable String cardNumber) {
        service.deleteByCardNumber(cardNumber);
    }
}
