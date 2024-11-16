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
@AllArgsConstructor
@NoArgsConstructor
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    private String name;
    private String location;
    private double totalArea;
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields;
}
