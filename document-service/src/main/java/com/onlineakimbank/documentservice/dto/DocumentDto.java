package com.onlineakimbank.documentservice.dto;

import java.time.LocalDateTime;

public record DocumentDto(
        String documentId,
        String userId,
        String type,
        String fileName,
        String contentType,
        Long size,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}