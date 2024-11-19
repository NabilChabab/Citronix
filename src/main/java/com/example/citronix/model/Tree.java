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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_uuid", referencedColumnName = "uuid")
    private Field field;

    @OneToMany(mappedBy = "tree")
    private List<HarvestDetail> harvestDetails;


    @Transient
    public int getAge() {
        return plantingDate != null ? LocalDate.now().getYear() - plantingDate.getYear() : 0;
    }

    @Transient
    public double getAnnualProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12.0;
        } else if (age <= 20) {
            return 20.0;
        }
        return 0;
    }

    @Transient
    public boolean isPlantingSeason() {
        if (plantingDate == null) {
            return false;
        }
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}
