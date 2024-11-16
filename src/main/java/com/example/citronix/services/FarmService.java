package com.example.citronix.services;

import com.example.citronix.model.Farm;
import com.example.citronix.services.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FarmService {


    Farm save(Farm farm);
    Farm update(UUID uuid, Farm farm);
    Farm findById(UUID uuid);
    Page<Farm> findAll(Pageable pageable);
    void delete(UUID farmId);

    List<SearchDTO> findByCriteria(SearchDTO searchDTO);
}
