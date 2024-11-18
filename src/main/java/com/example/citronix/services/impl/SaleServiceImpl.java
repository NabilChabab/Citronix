package com.example.citronix.services.impl;

import com.example.citronix.exceptions.InvalidUuidException;
import com.example.citronix.model.Harvest;
import com.example.citronix.model.Sale;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.repository.SaleRepository;
import com.example.citronix.services.SaleService;
import com.example.citronix.services.dto.SaleDTO;
import org.springframework.stereotype.Service;


@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
    }

    @Override
    public SaleDTO save(Sale sale) {
        Harvest harvest = harvestRepository.findById(sale.getHarvest().getUuid())
            .orElseThrow(() -> new InvalidUuidException("Invalid harvest UUID"));

        double revenue = harvest.getTotalQuantity() * sale.getUnitPrice();

        SaleDTO saleDTO = new SaleDTO(
            sale.getSaleDate(),
            sale.getUnitPrice(),
            sale.getClient(),
            revenue
        );

        sale.setHarvest(harvest);
        saleRepository.save(sale);

        return saleDTO;
    }
}
