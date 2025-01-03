package com.example.citronix.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Field {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private double area;

    @ManyToOne
    @JoinColumn(name = "farm_uuid")
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees;
}
