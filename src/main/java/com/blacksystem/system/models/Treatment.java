package com.blacksystem.system.models;

import com.blacksystem.system.models.enums.AdministrationRoute;
import com.blacksystem.system.models.enums.DosageUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "treatments")
@Getter
@Setter
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------------------------
    // DATOS PRINCIPALES
    // -------------------------
    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private int dosageAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DosageUnit dosageUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdministrationRoute administration;

    @Column(nullable = false)
    private int durationDays;

    @Column(nullable = false)
    private int intervalHours;

    @Column(nullable = false)
    private boolean wantsReminders;

    private String imageUrl;

    // -------------------------
    // RELACIÓN CON MASCOTA
    // -------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore
    private Pet pet;
}
