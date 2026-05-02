package com.onlineakimbank.cardservice.request;

import com.onlineakimbank.cardservice.entity.enums.AccountStatus;
import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanCardRequest(
        @NotBlank String userId,
        @NotBlank String accountId,
        @NotNull CardNetwork cardNetwork,
        @NotNull AccountStatus accountStatus
) {

}
