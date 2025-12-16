package com.blacksystem.system.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetVisitRequest {

    private String visitDate;
    private String reason;
    private String diagnosis;
    private String treatment;
    private String notes;

    private String vetName;
    private String clinic;

    private boolean wantsReminders;
    private String nextVisitDate;
}
