package com.example.citronix.repository;

import com.example.citronix.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field , UUID> {
    Page<Field> findAllByFarmUuid(UUID farmUuid, Pageable pageable);
}
