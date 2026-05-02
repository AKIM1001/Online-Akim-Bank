package com.onlineakimbank.adminservice.client;

import com.onlineakimbank.adminservice.dto.CardDto;
import com.onlineakimbank.adminservice.dto.enums.CardStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "card-service", url = "${services.card}")
public interface CardClient {

    @GetMapping("/cards/{id}")
    CardDto getCard(@PathVariable("id") String id);

    @GetMapping("/cards/account/{accountId}")
    List<CardDto> getCardsByAccount(@PathVariable("accountId") String accountId);

    @PatchMapping("/cards/{id}/status")
    void changeStatus(@PathVariable("id") String id,
                      @RequestParam("status") CardStatus status);
}
