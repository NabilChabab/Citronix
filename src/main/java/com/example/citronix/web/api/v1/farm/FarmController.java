package com.example.citronix.web.api.v1.farm;


import com.example.citronix.model.Farm;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.dto.SearchDTO;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.mapper.FarmMapper;
import com.example.citronix.web.vm.search.FarmSearchVM;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {



    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(@Qualifier("farmServiceImpl2") FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM){
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.CREATED);
    }

    @PutMapping("/update/{farm_uuid}")
    public ResponseEntity<FarmResponseVM> update(@PathVariable UUID farm_uuid, @RequestBody @Valid FarmVM farmVM){
        Farm farm = farmMapper.toEntity(farmVM);
        Farm updatedFarm = farmService.update(farm_uuid, farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farm_uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID farm_uuid){
        farmService.delete(farm_uuid);
        return new ResponseEntity<>("the farm delete successfully" , HttpStatus.OK);
    }

    @GetMapping("/find/{farm_uuid}")
    public ResponseEntity<FarmResponseVM> findById(@PathVariable UUID farm_uuid){
        Farm farm = farmService.findById(farm_uuid);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(farm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmResponseVM>> findAll(Pageable pageable){
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmResponseVM> farmResponseVM = farms.map(farmMapper::toResponseVM);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchDTO>> searchFarm(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String creationDate){

        LocalDate creationDateParsed = null;
        if (StringUtils.hasText(creationDate)) {
            try {
                creationDateParsed = LocalDate.parse(creationDate);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(null);
            }
        }
        SearchDTO searchDTO = new SearchDTO(name, location, creationDateParsed);
        List<SearchDTO> farms = farmService.findByCriteria(searchDTO);
        return ResponseEntity.ok(farms);
    }

    @GetMapping("/field-area-less-than")
    public ResponseEntity<List<FarmResponseVM>> getFarmsWithFieldAreaLessThan() {
        List<Farm> farms = farmService.findFarmsWithFieldAreaLessThan();
        List<FarmResponseVM> farmResponseVMs = farms.stream()
            .map(farmMapper::toResponseVM)
            .toList();
        return new ResponseEntity<>(farmResponseVMs, HttpStatus.OK);
    }
}
