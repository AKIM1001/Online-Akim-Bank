package com.onlineakimbank.transactionservice.controller;

import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.request.TransactionRequest;
import com.onlineakimbank.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionRequest request) {
        TransactionDto created = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/processing/{transactionId}")
    public ResponseEntity<Void> markProcessing(@PathVariable String transactionId) {
        transactionService.markProcessing(transactionId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/finalize/{transactionId}")
    public ResponseEntity<Void> finalizeTransaction(
            @PathVariable String transactionId,
            @RequestParam boolean success
    ) {
        transactionService.finalizeTransaction(transactionId, success);
        return ResponseEntity.ok().build();
    }
}
