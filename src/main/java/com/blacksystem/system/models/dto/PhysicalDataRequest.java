package com.blacksystem.system.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhysicalDataRequest {

    private Double weightKg;
    private Double heightCm; // opcional
    private Integer bcs;     // 1–5
    private String recordedAt;

}