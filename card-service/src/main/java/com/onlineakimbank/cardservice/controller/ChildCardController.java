package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.dto.ChildCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.ChildCardRequest;
import com.onlineakimbank.cardservice.service.ChildCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/card/child")
public class ChildCardController extends AbstractController<ChildCardDto, Card, ChildCardRequest>{

    @Autowired
    public ChildCardController(ChildCardService service,
                               ChildCardService childCardService) { super(service);
        this.childCardService = childCardService;
    }

    private final ChildCardService childCardService;

    @PostMapping("/{childCardId}/allocate-limit")
    public ResponseEntity<String> allocateLimit(
            @PathVariable String childCardId,
            @RequestParam String parentCardId,
            @RequestParam BigDecimal limit
    ) {
        childCardService.allocateLimitToChild(parentCardId, childCardId, limit);
        return ResponseEntity.ok("Limit allocated successfully");
    }

    @PostMapping("/{childCardId}/spend")
    public ResponseEntity<String> spend(
            @PathVariable String childCardId,
            @RequestParam BigDecimal amount
    ) {
        childCardService.spendFromChildCard(childCardId, amount);
        return ResponseEntity.ok("Amount spent successfully");
    }
}

