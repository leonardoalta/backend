package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.VetVisit;
import com.blacksystem.system.models.dto.VetVisitRequest;
import com.blacksystem.system.services.pets.PetService;
import com.blacksystem.system.services.VetVisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vet-visits")
@CrossOrigin(origins = "*")
public class VetVisitController {

    private final VetVisitService vetVisitService;
    private final PetService petService;

    public VetVisitController(VetVisitService vetVisitService,
                              PetService petService) {
        this.vetVisitService = vetVisitService;
        this.petService = petService;
    }

    // --------------------------------------------------
    // 🔥 CREAR VISITA
    // POST /api/vet-visits/{petId}
    // --------------------------------------------------
    @PostMapping(
            value = "/{petId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public VetVisit createVisit(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId,
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false)
            MultipartFile photo
    ) throws Exception {

        Pet pet = petService.getPetByIdAndUser(petId, user);

        VetVisitRequest req =
                new ObjectMapper().readValue(data, VetVisitRequest.class);

        return vetVisitService.createVisit(pet, req, photo);
    }

    // --------------------------------------------------
    // 🔥 ACTUALIZAR VISITA
    // PUT /api/vet-visits/{petId}/update/{id}
    // --------------------------------------------------
    @PutMapping(
            value = "/{petId}/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public VetVisit updateVisit(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId,
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false)
            MultipartFile photo
    ) throws Exception {

        Pet pet = petService.getPetByIdAndUser(petId, user);

        VetVisitRequest req =
                new ObjectMapper().readValue(data, VetVisitRequest.class);

        return vetVisitService.updateVisit(pet, id, req, photo);
    }

    // --------------------------------------------------
    // 🔥 LISTAR VISITAS
    // GET /api/vet-visits/{petId}/list
    // --------------------------------------------------
    @GetMapping("/{petId}/list")
    public List<VetVisit> listVisits(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return vetVisitService.getVisitsByPet(pet);
    }
}
