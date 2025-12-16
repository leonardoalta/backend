package com.blacksystem.system.services;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.VetVisit;
import com.blacksystem.system.models.AnnualVaccine;
import com.blacksystem.system.models.dto.DashboardWeeklyResponse;
import com.blacksystem.system.repositorys.VetVisitRepository;
import com.blacksystem.system.repositorys.vaccines.AnnualVaccineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final VetVisitRepository vetVisitRepository;
    private final AnnualVaccineRepository vaccineRepository;

    public DashboardService(
            VetVisitRepository vetVisitRepository,
            AnnualVaccineRepository vaccineRepository
    ) {
        this.vetVisitRepository = vetVisitRepository;
        this.vaccineRepository = vaccineRepository;
    }

    public DashboardWeeklyResponse getWeeklySummary(Pet pet) {

        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(7);

        List<DashboardWeeklyResponse.VetVisitItem> visits =
                vetVisitRepository
                        .findByPetAndWantsRemindersTrueAndNextVisitDateBetween(pet, today, end)
                        .stream()
                        .map(v -> new DashboardWeeklyResponse.VetVisitItem(
                                v.getId(),
                                v.getNextVisitDate().toString(),
                                v.getReason()
                        ))
                        .toList();

        List<DashboardWeeklyResponse.VaccineItem> vaccines =
                vaccineRepository
                        .findByPetAndWantsRemindersTrueAndNextVaccineDateBetween(pet, today, end)
                        .stream()
                        .map(v -> new DashboardWeeklyResponse.VaccineItem(
                                v.getId(),
                                v.getName(),
                                v.getNextVaccineDate().toString()
                        ))
                        .toList();

        return new DashboardWeeklyResponse(visits, vaccines);
    }
}
