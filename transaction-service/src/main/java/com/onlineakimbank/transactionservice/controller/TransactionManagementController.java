package com.onlineakimbank.transactionservice.controller;

import com.onlineakimbank.transactionservice.dto.TransactionDto;
import com.onlineakimbank.transactionservice.entity.enums.TransactionStatus;
import com.onlineakimbank.transactionservice.service.TransactionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction/management")
@RequiredArgsConstructor
public class TransactionManagementController {

    private final TransactionManagementService managementService;

    @GetMapping("/find/{transactionId}")
    public ResponseEntity<TransactionDto> findTransactionById(@PathVariable String transactionId) {
        TransactionDto tx = managementService.findByTransactionId(transactionId);
        return ResponseEntity.ok(tx);
    }

    @GetMapping("/find/{transactionNumber}")
    public ResponseEntity<List<TransactionDto>> findTransactionsByNumber(@PathVariable String transactionNumber) {
        List<TransactionDto> transactions = managementService.findByTransactionNumber(transactionNumber);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/delete/{transactionNumber}")
    public ResponseEntity<Void> deleteTransactionByNumber(@PathVariable String transactionNumber) {
        managementService.deleteByTransactionNumber(transactionNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status/{transactionId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String transactionId,
            @RequestParam TransactionStatus status
    ) {
        managementService.updateStatus(transactionId, status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/processing/{transactionId}")
    public ResponseEntity<TransactionDto> markAsProcessingAndDebit(
            @PathVariable String transactionId,
            @RequestParam BigDecimal amount
    ) {
        TransactionDto updated = managementService.markAsProcessingAndDebit(transactionId, amount)
                .orElseThrow(() -> new IllegalStateException("Transaction cannot be processed: " + transactionId));
        return ResponseEntity.ok(updated);
    }
}
