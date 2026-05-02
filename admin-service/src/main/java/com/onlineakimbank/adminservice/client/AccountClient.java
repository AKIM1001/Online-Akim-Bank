package com.onlineakimbank.adminservice.client;


import com.onlineakimbank.adminservice.dto.AccountDto;
import com.onlineakimbank.adminservice.dto.enums.State;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "${services.account}")
public interface AccountClient {

    @GetMapping("/accounts/{id}")
    AccountDto getAccount(@PathVariable("id") String id);

    @GetMapping("/accounts/user/{userId}")
    List<AccountDto> getAccountsByUser(@PathVariable("userId") String userId);

    @PatchMapping("/accounts/{id}/state")
    void changeState(@PathVariable("id") String id,
                     @RequestParam("state") State state);
}