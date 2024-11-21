package com.example.citronix.services.impl;

import com.example.citronix.exceptions.*;
import com.example.citronix.model.Field;
import com.example.citronix.model.Harvest;
import com.example.citronix.model.HarvestDetail;
import com.example.citronix.model.Tree;
import com.example.citronix.model.enums.Season;
import com.example.citronix.repository.HarvestDetailRepository;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.services.FieldService;
import com.example.citronix.services.HarvestService;
import com.example.citronix.services.TreeService;
import com.example.citronix.utils.SeasonUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;
    private final FieldService fieldService;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository, FieldService fieldService) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
        this.fieldService = fieldService;
    }


    @Override
    public Harvest save(Harvest harvest , UUID fieldUuid) {
        validateFieldAndSeason(harvest.getHarvestDate(), fieldUuid);

        List<Tree> availableTrees = getAvailableTreesForHarvest(fieldUuid, harvest.getHarvestDate());
        if (availableTrees.isEmpty()) {
            throw new TreeNotFoundException("All trees have been harvested this season.");
        }

        double totalQuantity = calculateTotalQuantity(availableTrees);

        Harvest harvestToSave = createHarvest(harvest, totalQuantity);
        Harvest savedHarvest = harvestRepository.save(harvestToSave);

        saveHarvestDetails(savedHarvest, availableTrees);

        return savedHarvest;

    }

    private void validateFieldAndSeason(LocalDate harvestDate, UUID fieldUuid) {
        if (fieldService.findById(fieldUuid) == null) {
            throw new IllegalArgumentException("Field not found.");
        }
        if (SeasonUtils.seasonFromDate(harvestDate) == null) {
            throw new IllegalArgumentException("Invalid season derived from the harvest date.");
        }
    }

    private List<Tree> getAvailableTreesForHarvest(UUID fieldUuid, LocalDate harvestDate) {
        Field fieldToHarvest = fieldService.findById(fieldUuid);
        Season harvestSeason = SeasonUtils.seasonFromDate(harvestDate);

        return fieldToHarvest.getTrees().stream()
            .filter(tree -> !harvestDetailRepository.existsByTreeAndHarvest_Season(tree, harvestSeason))
            .toList();
    }
    private double calculateTotalQuantity(List<Tree> trees) {
        return trees.stream()
            .mapToDouble(Tree::getAnnualProductivity)
            .sum();
    }

    private Harvest createHarvest(Harvest harvest, double totalQuantity) {
        return Harvest.builder()
            .harvestDate(harvest.getHarvestDate())
            .season(SeasonUtils.seasonFromDate(harvest.getHarvestDate()))
            .totalQuantity(totalQuantity)
            .build();
    }

    private void saveHarvestDetails(Harvest savedHarvest, List<Tree> trees) {
        List<HarvestDetail> harvestDetails = trees.stream()
            .map(tree -> HarvestDetail.builder()
                .harvest(savedHarvest)
                .tree(tree)
                .quantity(tree.getAnnualProductivity())
                .build())
            .toList();

        harvestDetailRepository.saveAll(harvestDetails);
    }

    @Override
    public Harvest findById(UUID uuid) {
        if (uuid == null){
            throw new InvalidUuidException("harvest ID is Required");
        }
        return harvestRepository.findById(uuid)
            .orElseThrow(()-> new HarvestNotFoundException("harvest Not Found"));
    }

    @Override
    public void delete(UUID uuid) {
        Harvest harvestToDelete = findById(uuid);
        harvestRepository.delete(harvestToDelete);
    }

    @Override
    public List<Harvest> findHarvestsBySeason(Season season) {
        return harvestRepository.findBySeason(season);
    }
}
