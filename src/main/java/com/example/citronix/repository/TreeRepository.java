package com.example.citronix.repository;

import com.example.citronix.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree , UUID> {
    long countByFieldUuid(UUID fieldUuid);
    List<Tree> findAllByFieldUuid(UUID fieldUuid);
    @Query("SELECT t FROM Tree t JOIN FETCH t.field WHERE t.uuid = :uuid")
    Optional<Tree> findByUuidWithField(@Param("uuid") UUID uuid);

    Optional<Tree> findByUuid(UUID uuid);
}
