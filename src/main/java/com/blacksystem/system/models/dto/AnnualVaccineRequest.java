package com.blacksystem.system.models.dto;

public class AnnualVaccineRequest {

    private String name;
    private String date;
    private String manufactureDate;
    private String lotNumber;
    private String vetName;
    private boolean wantsReminders;
    private String nextVaccineDate;

    // getters & setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getManufactureDate() { return manufactureDate; }
    public void setManufactureDate(String manufactureDate) { this.manufactureDate = manufactureDate; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public String getVetName() { return vetName; }
    public void setVetName(String vetName) { this.vetName = vetName; }

    public boolean isWantsReminders() { return wantsReminders; }
    public void setWantsReminders(boolean wantsReminders) { this.wantsReminders = wantsReminders; }

    public String getNextVaccineDate() { return nextVaccineDate; }
    public void setNextVaccineDate(String nextVaccineDate) { this.nextVaccineDate = nextVaccineDate; }
}
