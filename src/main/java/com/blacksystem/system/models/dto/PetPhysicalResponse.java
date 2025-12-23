package com.blacksystem.system.models.dto;

import com.blacksystem.system.models.enums.BodyConditionScore;
import java.time.LocalDate;

public class PetPhysicalResponse {

    private Long id;
    private Double weightKg;
    private Double heightCm;
    private BodyConditionScore bcs;
    private LocalDate recordedAt;

    public PetPhysicalResponse(
            Long id,
            Double weightKg,
            Double heightCm,
            BodyConditionScore bcs,
            LocalDate recordedAt
    ) {
        this.id = id;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.bcs = bcs;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public BodyConditionScore getBcs() {
        return bcs;
    }

    public LocalDate getRecordedAt() {
        return recordedAt;
    }
}
