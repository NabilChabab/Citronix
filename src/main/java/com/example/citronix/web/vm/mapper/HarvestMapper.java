package com.example.citronix.web.vm.mapper;


import com.example.citronix.model.Harvest;
import com.example.citronix.web.vm.harvest.HarvestResponseVM;
import com.example.citronix.web.vm.harvest.HarvestVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    Harvest toEntity(HarvestVM harvestVM);
    HarvestResponseVM toResponseVM(Harvest harvest);
}
