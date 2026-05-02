package com.onlineakimbank.adminservice.client;

import com.onlineakimbank.adminservice.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transaction-service", url = "${services.transaction}")
public interface TransactionClient {

    @GetMapping("/transactions/account/{accountId}")
    List<TransactionDto> getByAccount(@PathVariable("accountId") String accountId);

    @GetMapping("/transactions/{id}")
    TransactionDto getById(@PathVariable("id") String id);
}
