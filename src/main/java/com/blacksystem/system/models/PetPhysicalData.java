package com.blacksystem.system.models;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.enums.BodyConditionScore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pet_physical_data")
@Getter
@Setter
public class PetPhysicalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // 📏 altura (cm) – solo perros
    private Double heightCm;

    // ⚖️ peso (kg)
    private Double weightKg;

    // 🧠 Body Condition Score
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyConditionScore bcs;

    // 📅 fecha del registro
    @Column(nullable = false)
    private LocalDate recordedAt;
}
