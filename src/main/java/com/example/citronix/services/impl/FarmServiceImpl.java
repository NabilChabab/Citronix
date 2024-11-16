package com.example.citronix.services.impl;

import com.example.citronix.model.Farm;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.services.FarmSearchService;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmSearchService farmSearchService;

    public FarmServiceImpl(FarmRepository farmRepository, FarmSearchService farmSearchService) {
        this.farmRepository = farmRepository;
        this.farmSearchService = farmSearchService;
    }

    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new RuntimeException("Farm already exists");
        }

        return farmRepository.save(farm);
    }

    @Override
    public Farm update(UUID uuid ,Farm farm) {
        Farm farm1 = farmRepository.findById(uuid).orElseThrow(()->new RuntimeException("Farm not found"));
        farm1.setName(farm.getName());
        farm1.setLocation(farm.getLocation());
        farm1.setCreationDate(farm.getCreationDate());
        farm1.setTotalArea(farm.getTotalArea());
        return farmRepository.save(farm1);
    }

    @Override
    public Farm findById(UUID uuid) {
        return farmRepository.findById(uuid).orElseThrow(()->new RuntimeException("Farm not found"));
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID farmId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new RuntimeException("Farm not found"));
        farmRepository.delete(farm);
    }

    @Override
    public List<SearchDTO> findByCriteria(SearchDTO searchDTO) {
        return farmSearchService.findByCriteria(searchDTO);
    }

}
