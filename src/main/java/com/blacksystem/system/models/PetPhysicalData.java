package com.blacksystem.system.models;

import com.blacksystem.system.models.enums.BodyConditionScore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // 📏 altura (cm)
    private Double heightCm;

    // ⚖️ peso (kg)
    private Double weightKg;

    // 🧠 BCS
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyConditionScore bcs;

    // 📅 fecha lógica (la que ve el usuario)
    @Column(nullable = false)
    private LocalDate recordedAt;

    // ⏱️ fecha REAL de creación (CLAVE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 🔥 se asigna automáticamente
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
