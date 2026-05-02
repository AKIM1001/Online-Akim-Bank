package com.onlineakimbank.paymentservice.controller;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import com.onlineakimbank.paymentservice.service.PaymentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/management")
@RequiredArgsConstructor
public class PaymentManagementController {

    private final PaymentManagementService service;

    @GetMapping("/find/{paymentId}")
    public ResponseEntity<PaymentDto> findByPaymentId(@PathVariable String paymentId) {
        return ResponseEntity.ok(service.findByPaymentId(paymentId));
    }

    @GetMapping("/find/{accountId}")
    public ResponseEntity<List<PaymentDto>> findByAccountId(@PathVariable String accountId) {
        return ResponseEntity.ok(service.findByAccountId(accountId));
    }

    @GetMapping("/find/{accountNumber}")
    public ResponseEntity<List<PaymentDto>> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(service.findByAccountNumber(accountNumber));
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<PaymentDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Void> deleteByPaymentId(@PathVariable String paymentId) {
        service.deleteByPaymentId(paymentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteByAccountId(@PathVariable String accountId) {
        service.deleteByAccountId(accountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<Void> deleteByAccountNumber(@PathVariable String accountNumber) {
        service.deleteByAccountNumber(accountNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/status/{paymentId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String paymentId,
            @RequestParam PaymentStatus status
    ) {
        service.updateStatus(paymentId, status);
        return ResponseEntity.ok().build();
    }
}
