package com.onlineakimbank.documentservice.dto;

import com.onlineakimbank.documentservice.entity.enums.DocumentStatus;

public record UpdateStatusDto(
        DocumentStatus status
) {}
