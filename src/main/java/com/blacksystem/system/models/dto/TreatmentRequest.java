package com.blacksystem.system.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentRequest {

    private String medicineName;
    private String startDate; // yyyy-MM-dd
    private int dosageAmount;
    private String dosageUnit;
    private String administration;
    private int durationDays;
    private int intervalHours;
    private boolean wantsReminders;
}

