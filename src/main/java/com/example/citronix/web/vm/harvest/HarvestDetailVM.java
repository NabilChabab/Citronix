package com.example.citronix.web.vm.harvest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetailVM {

    @NotNull(message = "Tree UUID is required")
    private UUID treeUuid;
}
