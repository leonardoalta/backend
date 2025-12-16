package com.blacksystem.system.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DashboardWeeklyResponse {

    private List<VetVisitItem> vetVisits;
    private List<VaccineItem> vaccines;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class VetVisitItem {
        private Long id;
        private String date;
        private String reason;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class VaccineItem {
        private Long id;
        private String name;
        private String date;
    }
}
