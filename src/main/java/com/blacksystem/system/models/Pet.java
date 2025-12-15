package com.blacksystem.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private int age;

    private double weight;

    // GATO | PERRO | OTRO
    @Column(nullable = false)
    private String species;

    // solo cuando species == OTRO
    private String otherSpecies;

    // MASCULINO | FEMENINO
    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String color;

    private LocalDate adoptionDate;

    private boolean isSterilized;

    // URL o path guardado
    private String imageUrl;

    // 🔗 Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;
}
