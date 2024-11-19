package com.example.citronix.services.impl;

import com.example.citronix.exceptions.FarmExistsException;
import com.example.citronix.exceptions.FieldNotFoundException;
import com.example.citronix.model.Farm;
import com.example.citronix.model.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service("farmServiceImpl2")
public class FarmServiceImpl2 implements FarmService {

    private final FarmRepository farmRepository;

    public FarmServiceImpl2(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new FarmExistsException("Farm already exists");
        }
        if (farm.getFields() != null){
            for (Field field : farm.getFields()){
                field.setFarm(farm);
            }
        }
        else {
            throw new FieldNotFoundException("Fields are required");
        }
        return farmRepository.save(farm);
    }

    @Override
    public Farm update(UUID uuid, Farm farm) {
        return null;
    }

    @Override
    public Farm findById(UUID uuid) {
        return null;
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(UUID farmId) {

    }

    @Override
    public List<SearchDTO> findByCriteria(SearchDTO searchDTO) {
        return List.of();
    }

    @Override
    public List<Farm> findFarmsWithFieldAreaLessThan() {
        return List.of();
    }
}
