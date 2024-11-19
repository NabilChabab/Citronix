package com.example.citronix.web.api.v1.harvest;


import com.example.citronix.model.Harvest;
import com.example.citronix.services.HarvestService;
import com.example.citronix.web.vm.harvest.HarvestResponseVM;
import com.example.citronix.web.vm.harvest.HarvestVM;
import com.example.citronix.web.vm.mapper.HarvestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    public HarvestController(HarvestService harvestService, HarvestMapper harvestMapper) {
        this.harvestService = harvestService;
        this.harvestMapper = harvestMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<HarvestResponseVM> save(@RequestBody Harvest harvest) {
        Harvest createdHarvest = harvestService.save(harvest);
        HarvestResponseVM responseVM = harvestMapper.toResponseVM(createdHarvest);
        return ResponseEntity.ok(responseVM);
    }
}
