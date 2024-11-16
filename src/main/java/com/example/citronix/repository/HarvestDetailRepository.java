package com.example.citronix.repository;

import com.example.citronix.model.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail , UUID> {
}
