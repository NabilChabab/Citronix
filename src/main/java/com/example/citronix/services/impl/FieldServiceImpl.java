package com.example.citronix.services.impl;

import com.example.citronix.exceptions.FarmNotFoundException;
import com.example.citronix.exceptions.FarmSizeException;
import com.example.citronix.exceptions.FieldAreaException;
import com.example.citronix.exceptions.FieldNotFoundException;
import com.example.citronix.model.Farm;
import com.example.citronix.model.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.FieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field save(UUID farmUuid, Field field) {
        Farm farm = farmRepository.findById(farmUuid).orElseThrow(() ->
            new FarmNotFoundException("Farm not found"));

        validateField(farm, field);

        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field update(UUID fieldUuid, Field field) {
        Field existingField = fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new FieldNotFoundException("Field not found"));

        Farm farm = existingField.getFarm();

        validateField(farm, field);

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(UUID fieldUuid) {
        return fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new FieldNotFoundException("Field not found"));
    }

    @Override
    public Page<Field> findAllByFarm(UUID farmUuid, Pageable pageable) {
        return fieldRepository.findAllByFarmUuid(farmUuid, pageable);
    }

    @Override
    public void delete(UUID fieldUuid) {
        Field field = fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new FieldNotFoundException("Field not found"));
        fieldRepository.delete(field);
    }

    private void validateField(Farm farm, Field newField) {
        double totalFieldArea = farm.getFields().stream()
            .filter(field -> !field.getUuid().equals(newField.getUuid()))
            .mapToDouble(Field::getArea)
            .sum() + newField.getArea();

        if (newField.getArea() < 0.1) {
            throw new FieldAreaException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

        if (newField.getArea() > (farm.getTotalArea() * 0.5)) {
            throw new FieldAreaException("Field area cannot exceed 50% of the farm's total area");
        }

        if (totalFieldArea >= farm.getTotalArea()) {
            throw new FieldAreaException("Total field area must be less than the farm's total area");
        }

        if (farm.getFields().size() >= 10) {
            throw new FarmSizeException("A farm cannot have more than 10 fields");
        }
    }
}
