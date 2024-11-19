package com.example.citronix.services.impl;

import com.example.citronix.exceptions.*;
import com.example.citronix.model.Harvest;
import com.example.citronix.model.HarvestDetail;
import com.example.citronix.model.Tree;
import com.example.citronix.model.enums.Season;
import com.example.citronix.repository.HarvestDetailRepository;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.services.HarvestService;
import com.example.citronix.services.TreeService;
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
    private final TreeRepository treeRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository, TreeRepository treeRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
        this.treeRepository = treeRepository;
    }


    @Override
    public Harvest save(Harvest harvest) {
        // Validate the harvest date
        validateHarvestPeriod(harvest.getHarvestDate());

        // Validate and retrieve the field UUID
        if (harvest.getHarvestDetails() == null || harvest.getHarvestDetails().isEmpty()) {
            throw new IllegalArgumentException("Harvest must include at least one harvest detail with a tree UUID.");
        }

        // Fetch the first tree from the harvest details
        HarvestDetail firstHarvestDetail = harvest.getHarvestDetails().get(0);
        UUID treeUuid = firstHarvestDetail.getTree().getUuid();

        // Fetch the Tree with the Field
        Tree tree = treeRepository.findByUuidWithField(treeUuid)
            .orElseThrow(() -> new IllegalArgumentException("Tree not found or not associated with any field"));

        if (tree.getField() == null) {
            throw new IllegalStateException("The tree is not associated with any field.");
        }

        UUID fieldUuid = tree.getField().getUuid();

        // Validate unique harvest for the season and field
        validateUniqueHarvestForSeason(harvest.getSeason(), fieldUuid);
        validateTreeNotAlreadyHarvested(tree, harvest.getSeason());

        // Fetch all trees in the field
        List<Tree> trees = treeRepository.findAllByFieldUuid(fieldUuid);

        if (trees.isEmpty()) {
            throw new IllegalStateException("No trees found in the specified field.");
        }

        // Generate HarvestDetails for all trees in the field
        List<HarvestDetail> harvestDetails = trees.stream()
            .map(currentTree -> createHarvestDetail(harvest, currentTree))
            .collect(Collectors.toList());

        // Set the generated HarvestDetails to the Harvest
        harvest.setHarvestDetails(harvestDetails);

        // Save the Harvest along with HarvestDetails (cascade save due to @OneToMany relationship)
        return harvestRepository.save(harvest);
    }


    private HarvestDetail createHarvestDetail(Harvest harvest, Tree tree) {
        HarvestDetail detail = new HarvestDetail();
        detail.setHarvest(harvest);
        detail.setTree(tree);
        detail.setQuantity(tree.getAnnualProductivity());
        return detail;
    }

    private void validateUniqueHarvestForSeason(Season season, UUID fieldUuid) {
        boolean exists = harvestRepository.existsBySeasonAndHarvestDetails_Tree_Field_Uuid(season, fieldUuid);
        if (exists) {
            throw new HarvestExistsForSeasonException("A harvest already exists for this season and field");
        }
    }

    private void validateTreeNotAlreadyHarvested(Tree tree, Season season) {
        boolean exists = harvestDetailRepository.existsByTreeAndHarvest_Season(tree, season);
        if (exists) {
            throw new TreeHarvestedForSeason("Tree already harvested for this season");
        }
    }

    private void validateHarvestPeriod(LocalDate date) {
        Month month = date.getMonth();
        if (!(month == Month.MARCH || month == Month.APRIL || month == Month.MAY)) {
            throw new HarvestPlantingException("Harvests can only occur in March, April, or May");
        }
    }
}
