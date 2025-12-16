package com.blacksystem.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "annual_vaccines")
@Getter
@Setter
public class AnnualVaccine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaccine")
    private Long id;

    @Column(nullable = false)
    private String name;

    // Fecha aplicación
    @Column(nullable = false)
    private LocalDate date;

    // Fecha fabricación
    private LocalDate manufactureDate;

    @Column(nullable = false)
    private String lotNumber;

    @Column(nullable = false)
    private String vetName;

    // Recordatorios
    private boolean wantsReminders;

    private LocalDate nextVaccineDate;

    // Imagen (Cloudinary)
    private String photoUrl;

    // 🔗 Relación con mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore
    private Pet pet;
}
