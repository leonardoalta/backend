package com.blacksystem.system.models.dto;

import lombok.Data;

@Data
public class PetRequest {

    private String name;
    private int age;
    private double weight;
    private String species;        // Gato | Perro | Otro
    private String otherSpecies;
    private String gender;         // Masculino | Femenino
    private String color;
    private String adoptionDate;   // yyyy-MM-dd
    private boolean isSterilized;
}
