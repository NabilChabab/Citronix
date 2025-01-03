package com.example.citronix.repository;

import com.example.citronix.model.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm , UUID> {

    Optional<Farm> findByName(String name);
    Page<Farm> findAll(Pageable pageable);

    @Query("SELECT f FROM Farm f JOIN f.fields fi GROUP BY f HAVING SUM(fi.area) < 4000")
    List<Farm> findFarmsWithFieldAreaLessThan();
}
