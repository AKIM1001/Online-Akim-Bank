package com.onlineakimbank.documentservice.mapper;

import com.onlineakimbank.documentservice.dto.DocumentDto;
import com.onlineakimbank.documentservice.entity.Document;

public class DocumentMapper {

    public static DocumentDto toDto(Document doc) {
        return new DocumentDto(
                doc.getDocumentId(),
                doc.getUserId(),
                doc.getType().name(),
                doc.getFileName(),
                doc.getContentType(),
                doc.getSize(),
                doc.getStatus().name(),
                doc.getCreatedAt(),
                doc.getUpdatedAt()
        );
    }
}
