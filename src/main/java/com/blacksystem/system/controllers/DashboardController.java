package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.DashboardWeeklyResponse;
import com.blacksystem.system.services.DashboardService;
import com.blacksystem.system.services.pets.PetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;
    private final PetService petService;

    public DashboardController(
            DashboardService dashboardService,
            PetService petService
    ) {
        this.dashboardService = dashboardService;
        this.petService = petService;
    }

    @GetMapping("/weekly/{petId}")
    public DashboardWeeklyResponse weekly(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return dashboardService.getWeeklySummary(pet);
    }
}
