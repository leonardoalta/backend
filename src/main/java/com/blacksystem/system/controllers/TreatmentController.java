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
@RequestMapping("/treatment")
@CrossOrigin(origins = "*")
public class TreatmentController {

    private final TreatmentService treatmentService;
    private final PetService petService;

    public TreatmentController(TreatmentService treatmentService,
                               PetService petService) {
        this.treatmentService = treatmentService;
        this.petService = petService;
    }

    // -----------------------------------------
    // 🔥 CREAR TRATAMIENTO
    // -----------------------------------------
    @PostMapping(
            value = "/new",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Treatment createTreatment(
            @AuthenticationPrincipal User user,
            @RequestPart("data") String data,
            @RequestPart(value = "medicineImage", required = false)
            MultipartFile image
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TreatmentRequest req = mapper.readValue(data, TreatmentRequest.class);

        // ⚠️ Por ahora: 1 mascota activa
        Pet pet = petService.getPetsByUser(user).get(0);

        return treatmentService.createTreatment(pet, req, image);
    }

    // -----------------------------------------
    // 🔥 LISTAR TRATAMIENTOS
    // -----------------------------------------
    @GetMapping("/list")
    public List<Treatment> list(@AuthenticationPrincipal User user) {
        Pet pet = petService.getPetsByUser(user).get(0);
        return treatmentService.getTreatmentsByPet(pet);
    }
}
