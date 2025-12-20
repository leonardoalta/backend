package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.PetPhysicalData;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.PhysicalDataRequest;
import com.blacksystem.system.services.PetPhysicalService;
import com.blacksystem.system.services.pets.PetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets/{petId}/physical")
@CrossOrigin(origins = "*")
public class PetPhysicalController {

    private final PetService petService;
    private final PetPhysicalService physicalService;

    public PetPhysicalController(
            PetService petService,
            PetPhysicalService physicalService
    ) {
        this.petService = petService;
        this.physicalService = physicalService;
    }

    // 📥 Registrar control físico
    @PostMapping
    public PetPhysicalData save(
            @PathVariable Long petId,
            @AuthenticationPrincipal User user,
            @RequestBody PhysicalDataRequest request
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return physicalService.save(pet, request);
    }

    // 📊 Historial
    @GetMapping
    public List<PetPhysicalData> history(
            @PathVariable Long petId,
            @AuthenticationPrincipal User user
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return physicalService.getHistory(pet);
    }

    // 📌 Último registro
    @GetMapping("/latest")
    public PetPhysicalData latest(
            @PathVariable Long petId,
            @AuthenticationPrincipal User user
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return physicalService.getLatest(pet);
    }
}

