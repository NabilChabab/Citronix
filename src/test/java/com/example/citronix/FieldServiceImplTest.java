package com.example.citronix;

import com.example.citronix.exceptions.FarmNotFoundException;
import com.example.citronix.exceptions.FieldAreaException;
import com.example.citronix.exceptions.FieldNotFoundException;
import com.example.citronix.model.Farm;
import com.example.citronix.model.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.services.impl.FieldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FieldServiceImplTest {


    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private UUID farmUuid;
    private UUID fieldUuid;
    private Farm mockFarm;
    private Field mockField;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        farmUuid = UUID.randomUUID();
        fieldUuid = UUID.randomUUID();

        mockFarm = new Farm();
        mockFarm.setUuid(farmUuid);
        mockFarm.setTotalArea(100.0);
        mockFarm.setFields(Collections.emptyList());

        mockField = new Field();
        mockField.setUuid(fieldUuid);
        mockField.setArea(5.0);
    }

    @Test
    void save_ShouldSaveField_WhenValid() {
        when(farmRepository.findById(farmUuid)).thenReturn(Optional.of(mockFarm));
        when(fieldRepository.save(any(Field.class))).thenReturn(mockField);

        Field savedField = fieldService.save(farmUuid, mockField);

        assertNotNull(savedField);
        assertEquals(5.0, savedField.getArea());
        verify(farmRepository).findById(farmUuid);
        verify(fieldRepository).save(mockField);
    }

    @Test
    void save_ShouldThrowFarmNotFoundException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(farmUuid)).thenReturn(Optional.empty());

        assertThrows(FarmNotFoundException.class, () -> fieldService.save(farmUuid, mockField));
        verify(farmRepository).findById(farmUuid);
        verify(fieldRepository, never()).save(any(Field.class));
    }

    @Test
    void save_ShouldThrowFieldAreaException_WhenFieldAreaTooSmall() {
        mockField.setArea(0.05);
        when(farmRepository.findById(farmUuid)).thenReturn(Optional.of(mockFarm));

        assertThrows(FieldAreaException.class, () -> fieldService.save(farmUuid, mockField));
        verify(farmRepository).findById(farmUuid);
        verify(fieldRepository, never()).save(any(Field.class));
    }

    @Test
    void update_ShouldUpdateField_WhenValid() {

        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.of(mockField));
        mockField.setArea(10.0);
        when(fieldRepository.save(any(Field.class))).thenReturn(mockField);


        Field updatedField = fieldService.update(fieldUuid, mockField);


        assertNotNull(updatedField);
        assertEquals(10.0, updatedField.getArea());
        verify(fieldRepository).findById(fieldUuid);
        verify(fieldRepository).save(mockField);
    }

    @Test
    void update_ShouldThrowFieldNotFoundException_WhenFieldDoesNotExist() {

        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.empty());


        assertThrows(FieldNotFoundException.class, () -> fieldService.update(fieldUuid, mockField));
        verify(fieldRepository).findById(fieldUuid);
        verify(fieldRepository, never()).save(any(Field.class));
    }

    @Test
    void findById_ShouldReturnField_WhenFieldExists() {

        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.of(mockField));


        Field foundField = fieldService.findById(fieldUuid);


        assertNotNull(foundField);
        assertEquals(fieldUuid, foundField.getUuid());
        verify(fieldRepository).findById(fieldUuid);
    }

    @Test
    void findById_ShouldThrowFieldNotFoundException_WhenFieldDoesNotExist() {

        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.empty());


        assertThrows(FieldNotFoundException.class, () -> fieldService.findById(fieldUuid));
        verify(fieldRepository).findById(fieldUuid);
    }

    @Test
    void findAllByFarm_ShouldReturnPageOfFields() {

        Page<Field> fieldPage = new PageImpl<>(Collections.singletonList(mockField));
        when(fieldRepository.findAllByFarmUuid(eq(farmUuid), any(Pageable.class))).thenReturn(fieldPage);


        Page<Field> result = fieldService.findAllByFarm(farmUuid, Pageable.unpaged());


        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(fieldRepository).findAllByFarmUuid(eq(farmUuid), any(Pageable.class));
    }

    @Test
    void delete_ShouldDeleteField_WhenFieldExists() {

        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.of(mockField));


        fieldService.delete(fieldUuid);


        verify(fieldRepository).findById(fieldUuid);
        verify(fieldRepository).delete(mockField);
    }

    @Test
    void delete_ShouldThrowFieldNotFoundException_WhenFieldDoesNotExist() {
        when(fieldRepository.findById(fieldUuid)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> fieldService.delete(fieldUuid));
        verify(fieldRepository).findById(fieldUuid);
        verify(fieldRepository, never()).delete(any(Field.class));
    }
}
