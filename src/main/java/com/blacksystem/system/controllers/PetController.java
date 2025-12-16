package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.PetRequest;
import com.blacksystem.system.services.pets.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Pet registerPet(
            @AuthenticationPrincipal User user,
            @RequestPart("data") PetRequest request,
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
