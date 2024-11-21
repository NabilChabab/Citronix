package com.example.citronix.web.vm.harvest;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponseVM {
    private LocalDate harvestDate;
    private String season;
    private double totalQuantity;
}
