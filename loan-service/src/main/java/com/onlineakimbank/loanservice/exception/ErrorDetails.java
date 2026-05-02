package com.onlineakimbank.loanservice.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message,
                           String path, String errorCode) {
}

