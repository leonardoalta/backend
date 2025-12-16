package com.blacksystem.system.controllers;

import com.blacksystem.system.models.AnnualVaccine;
import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.AnnualVaccineRequest;
import com.blacksystem.system.services.pets.PetService;
import com.blacksystem.system.services.vaccines.AnnualVaccineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@CrossOrigin(origins = "*")
public class AnnualVaccineController {

    private final AnnualVaccineService vaccineService;
    private final PetService petService;
    private final ObjectMapper mapper = new ObjectMapper();

    public AnnualVaccineController(
            AnnualVaccineService vaccineService,
            PetService petService
    ) {
        this.vaccineService = vaccineService;
        this.petService = petService;
    }

    // =========================================================
    // 🟢 CREAR VACUNA (MISMO PATRÓN QUE TRATAMIENTO)
    // =========================================================
    @PostMapping(
            value = "/{petId}/annual",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AnnualVaccine create(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId,
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws Exception {

        AnnualVaccineRequest req =
                mapper.readValue(data, AnnualVaccineRequest.class);

        Pet pet = petService.getPetByIdAndUser(petId, user);
        return vaccineService.create(pet, req, photo);
    }

    // =========================================================
    // 🟡 ACTUALIZAR VACUNA
    // =========================================================
    @PutMapping(
            value = "/{petId}/annual/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AnnualVaccine update(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId,
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws Exception {

        AnnualVaccineRequest req =
                mapper.readValue(data, AnnualVaccineRequest.class);

        Pet pet = petService.getPetByIdAndUser(petId, user);
        AnnualVaccine vaccine = vaccineService.getByIdAndPet(id, pet);

        return vaccineService.update(vaccine, req, photo);
    }

    // =========================================================
    // 🔵 LISTAR VACUNAS
    // =========================================================
    @GetMapping("/{petId}/annual/list")
    public List<AnnualVaccine> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long petId
    ) {
        Pet pet = petService.getPetByIdAndUser(petId, user);
        return vaccineService.listByPet(pet);
    }
}
