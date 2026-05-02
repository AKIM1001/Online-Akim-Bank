package com.onlineakimbank.documentservice.dto;

public record UploadDto(
        String documentId,
        com.onlineakimbank.documentservice.entity.enums.DocumentStatus status
) {}
