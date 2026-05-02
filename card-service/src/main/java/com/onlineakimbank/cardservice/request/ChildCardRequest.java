package com.onlineakimbank.cardservice.request;

import com.onlineakimbank.cardservice.entity.enums.CardNetwork;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ChildCardRequest(
        @NotBlank String userId,
        @NotBlank String accountId,
        @NotNull CardNetwork cardNetwork,

        @NotBlank String parentCardId,

        @NotNull
        @Positive
        BigDecimal spendingLimit,

        @Size(max = 50)
        String cardAlias,

        @Min(1)
        @Max(10)
        Integer expiryYears
) {
}
