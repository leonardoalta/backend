package com.blacksystem.system.models.dto;

public class PetRequest {

    private String name;
    private int age;
    private double weight;
    private String species;
    private String otherSpecies;
    private String gender;
    private String color;
    private String adoptionDate;
    private boolean sterilized;

    // getters y setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getOtherSpecies() { return otherSpecies; }
    public void setOtherSpecies(String otherSpecies) { this.otherSpecies = otherSpecies; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getAdoptionDate() { return adoptionDate; }
    public void setAdoptionDate(String adoptionDate) { this.adoptionDate = adoptionDate; }

    public boolean isSterilized() { return sterilized; }
    public void setSterilized(boolean sterilized) { this.sterilized = sterilized; }
}
