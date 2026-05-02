package com.onlineakimbank.paymentservice.controller;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.request.PaymentRequest;
import com.onlineakimbank.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentRequest request) {

        PaymentDto payment = paymentService.createPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payment);
    }

    @PostMapping("/save/finalize/{paymentId}")
    public ResponseEntity<Void> finalizePayment(
            @PathVariable String paymentId,
            @RequestParam boolean success,
            @RequestParam(required = false) String reason
    ) {

        paymentService.finalizePayment(paymentId, success, reason);

        return ResponseEntity.ok().build();
    }
}