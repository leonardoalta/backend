package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.PetRequest;
import com.blacksystem.system.services.pets.PetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // 🐾 REGISTRAR MASCOTA
    @PostMapping(consumes = "multipart/form-data")
    public Pet registerPet(
            @AuthenticationPrincipal User user,
            @ModelAttribute PetRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return petService.registerPet(user, request, photo);
    }

    // 🐾 OBTENER MIS MASCOTAS
    @GetMapping("/all")
    public List<Pet> getMyPets(@AuthenticationPrincipal User user) {
        return petService.getPetsByUser(user);
    }
}
