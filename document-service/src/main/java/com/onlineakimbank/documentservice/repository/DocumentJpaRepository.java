package com.onlineakimbank.documentservice.repository;

import com.onlineakimbank.documentservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentJpaRepository extends JpaRepository<Document, String> {
    List<Document> findAllByUserId(String userId);
    void deleteByDocumentId(String documentId);
}
