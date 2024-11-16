package com.example.citronix.repository;

import com.example.citronix.model.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest , UUID> {
}
