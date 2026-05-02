package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.dto.LoanCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.LoanCardRequest;
import com.onlineakimbank.cardservice.service.LoanCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card/loan")
public class LoanCardController extends AbstractController<LoanCardDto, Card, LoanCardRequest> {

    private final LoanCardService loanCardService;

    @Autowired
    public LoanCardController(LoanCardService service, LoanCardService loanCardService) {
        super(service);
        this.loanCardService = loanCardService;
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<LoanCardDto> save(@RequestBody LoanCardRequest request) {
        LoanCardDto dto = loanCardService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{cardId}/spend")
    public ResponseEntity<Void> spendFromLoanCard(
            @PathVariable String cardId,
            @RequestParam BigDecimal amount
    ) {
        loanCardService.spendFromLoanCard(cardId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/pay")
    public ResponseEntity<Void> payLoan(
            @PathVariable String cardId,
            @RequestParam BigDecimal amount
    ) {
        loanCardService.payLoan(cardId, amount);
        return ResponseEntity.ok().build();
    }
}





