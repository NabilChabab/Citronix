package com.example.citronix.web.vm.harvest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestVM {

    @NotNull(message = "Harvest date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate harvestDate;
}
