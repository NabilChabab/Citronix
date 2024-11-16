package com.example.citronix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_uuid")
    private Field field;

    @OneToMany(mappedBy = "tree")
    private List<HarvestDetail> harvestDetails;
}
