package com.onlineakimbank.documentservice.entity;

import com.onlineakimbank.documentservice.entity.enums.DocumentStatus;
import com.onlineakimbank.documentservice.entity.enums.DocumentType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class Document {

    @Id
    private String documentId;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, unique = true)
    private String storageKey;

    private Long size;

    private String contentType;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String checksum;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
