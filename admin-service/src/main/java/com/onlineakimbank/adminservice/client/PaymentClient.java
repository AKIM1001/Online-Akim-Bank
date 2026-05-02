package com.onlineakimbank.adminservice.client;

import com.onlineakimbank.adminservice.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payment-service", url = "${services.payment}")
public interface PaymentClient {

    @GetMapping("/payments/account/{accountId}")
    List<PaymentDto> getByAccount(@PathVariable("accountId") String accountId);

    @GetMapping("/payments/{id}")
    PaymentDto getById(@PathVariable("id") String id);
}