package com.example.citronix.services;

import com.example.citronix.model.Harvest;
import com.example.citronix.model.HarvestDetail;
import com.example.citronix.model.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestService {

    Harvest save(Harvest harvest , UUID fieldUuid);
    Harvest findById(UUID uuid);
    void delete(UUID uuid);
    List<Harvest> findHarvestsBySeason(Season season);
}
