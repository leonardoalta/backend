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
    private final ObjectMapper mapper = new ObjectMapper();

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pet registerPet(
            @AuthenticationPrincipal User user,
            @RequestParam("data") String data,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws Exception {

        PetRequest request = mapper.readValue(data, PetRequest.class);
        return petService.registerPet(user, request, photo);
    }

    @GetMapping("/all")
    public List<Pet> getMyPets(@AuthenticationPrincipal User user) {
        return petService.getPetsByUser(user);
    }
}
