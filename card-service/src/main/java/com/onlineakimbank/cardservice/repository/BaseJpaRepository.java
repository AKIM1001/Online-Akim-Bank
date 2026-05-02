package com.onlineakimbank.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseJpaRepository<T>
        extends JpaRepository<T, String> {
}

