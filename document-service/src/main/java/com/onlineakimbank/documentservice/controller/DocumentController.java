package com.onlineakimbank.documentservice.controller;

import com.onlineakimbank.documentservice.dto.DocumentDto;
import com.onlineakimbank.documentservice.dto.UploadDto;
import com.onlineakimbank.documentservice.entity.Document;
import com.onlineakimbank.documentservice.entity.enums.DocumentStatus;
import com.onlineakimbank.documentservice.entity.enums.DocumentType;
import com.onlineakimbank.documentservice.mapper.DocumentMapper;
import com.onlineakimbank.documentservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public UploadDto upload(
            @RequestParam MultipartFile file,
            @RequestParam String userId,
            @RequestParam DocumentType type
    ) {
        return documentService.upload(file, userId, type);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws Exception {

        InputStream stream = documentService.download(id);
        byte[] data = stream.readAllBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "[attachment]")
                .body(data);
    }

    @GetMapping("/find/{id}")
    public DocumentDto getById(@PathVariable String id) {
        return documentService.findById(id);
    }

    @GetMapping("/find/{userId}")
    public List<DocumentDto> getByUser(@PathVariable String userId) {
        return documentService.findByUserId(userId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        documentService.delete(id);
    }

    @PatchMapping("/status/{id}")
    public DocumentDto updateStatus(
            @PathVariable String id,
            @RequestParam DocumentStatus status
    ) {
        return documentService.updateStatus(id, status);
    }
}