package com.blacksystem.system.controllers;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.Treatment;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.TreatmentRequest;
import com.blacksystem.system.services.pets.PetService;
import com.blacksystem.system.services.TreatmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/treatments")
@CrossOrigin(origins = "*")
public class TreatmentController {

    private final TreatmentService treatmentService;
    private final PetService petService;
    private final ObjectMapper mapper = new ObjectMapper();

    public TreatmentController(
            TreatmentService treatmentService,
            PetService petService
    ) {
        this.treatmentService = treatmentService;
        this.petService = petService;
    }

    // =========================================================
    // 🟢 CREAR TRATAMIENTO (POR MASCOTA)
    // =========================================================
    @PostMapping(
            value = "/{petId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Treatment create(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId,
            @RequestPart("data") String data,
            @RequestPart(value = "medicineImage", required = false)
            MultipartFile image
    ) throws Exception {

        TreatmentRequest req =
                mapper.readValue(data, TreatmentRequest.class);

        Pet pet = petService.getPetByIdAndUser(petId, user);

        return treatmentService.createTreatment(pet, req, image);
    }

    // =========================================================
    // 🔵 LISTAR TRATAMIENTOS POR MASCOTA
    // =========================================================
    @GetMapping("/{petId}/list")
    public List<Treatment> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return treatmentService.getTreatmentsByPet(pet);
    }
}
