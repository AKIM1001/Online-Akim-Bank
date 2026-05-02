package com.onlineakimbank.documentservice.service;

import com.onlineakimbank.documentservice.dto.DocumentDto;
import com.onlineakimbank.documentservice.dto.UploadDto;
import com.onlineakimbank.documentservice.entity.Document;
import com.onlineakimbank.documentservice.entity.enums.DocumentStatus;
import com.onlineakimbank.documentservice.entity.enums.DocumentType;
import com.onlineakimbank.documentservice.mapper.DocumentMapper;
import com.onlineakimbank.documentservice.repository.DocumentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentJpaRepository repository;
    private final StorageService storageService;

    public UploadDto upload(MultipartFile file, String userId, DocumentType type) {

        String key = buildKey(userId, file.getOriginalFilename());

        storageService.upload(file, key);

        Document doc = new Document();
        doc.setDocumentId(UUID.randomUUID().toString());
        doc.setUserId(userId);
        doc.setType(type);
        doc.setFileName(file.getOriginalFilename());
        doc.setStorageKey(key);
        doc.setSize(file.getSize());
        doc.setContentType(file.getContentType());
        doc.setStatus(DocumentStatus.READY);
        doc.setCreatedAt(LocalDateTime.now());
        doc.setUpdatedAt(LocalDateTime.now());

        repository.save(doc);

        return new UploadDto(doc.getDocumentId(), doc.getStatus());
    }

    public InputStream download(String documentId) {
        Document doc = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("[Document not found]"));

        return storageService.download(doc.getStorageKey());
    }

    public DocumentDto findById(String documentId) {
        return repository.findById(documentId)
                .map(DocumentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("[Document not found]"));
    }

    public List<DocumentDto> findByUserId(String userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(DocumentMapper::toDto)
                .toList();
    }

    public void delete(String documentId) {
        Document doc = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("[Document not found]"));

        try {
            storageService.deleteByDocumentId(doc.getStorageKey());
        } catch (Exception ignored) {}

        repository.delete(doc);
    }

    public DocumentDto updateStatus(String documentId, DocumentStatus status) {
        Document doc = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("[Document not found]"));

        doc.setStatus(status);
        doc.setUpdatedAt(LocalDateTime.now());

        return DocumentMapper.toDto(repository.save(doc));
    }

    public DocumentDto rename(String documentId, String newFileName) {
        Document doc = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("[Document not found]"));

        doc.setFileName(newFileName);
        doc.setUpdatedAt(LocalDateTime.now());

        return DocumentMapper.toDto(repository.save(doc));
    }

    private String buildKey(String userId, String fileName) {
        return "documents/" + userId + "/" + UUID.randomUUID() + "-" + fileName;
    }
}