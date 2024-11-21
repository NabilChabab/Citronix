package com.example.citronix.model;

import com.example.citronix.model.enums.Season;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "harvest_uuid")
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_uuid")
    private Tree tree;

}
