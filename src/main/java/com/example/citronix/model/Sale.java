package com.example.citronix.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private LocalDate saleDate;
    private double unitPrice;
    private String client;

    @ManyToOne
    @JoinColumn(name = "harvest_uuid")
    private Harvest harvest;
}
