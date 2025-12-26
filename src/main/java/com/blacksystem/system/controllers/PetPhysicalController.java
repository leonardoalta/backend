    package com.blacksystem.system.controllers;

    import com.blacksystem.system.models.User;
    import com.blacksystem.system.models.dto.PhysicalDataRequest;
    import com.blacksystem.system.models.dto.PetPhysicalResponse;
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

        // =====================================================
        // 📥 REGISTRAR CONTROL FÍSICO
        // =====================================================
        @PostMapping
        public void save(
                @PathVariable Long petId,
                @AuthenticationPrincipal User user,
                @RequestBody PhysicalDataRequest request
        ) {
            physicalService.save(
                    petService.getPetByIdAndUser(petId, user),
                    request
            );
        }

        // =====================================================
        // 📊 HISTORIAL
        // =====================================================
        @GetMapping
        public List<PetPhysicalResponse> history(
                @PathVariable Long petId,
                @AuthenticationPrincipal User user
        ) {
            petService.getPetByIdAndUser(petId, user);
            return physicalService.getHistory(petId);
        }

        // =====================================================
        // 📌 ÚLTIMO REGISTRO
        // =====================================================
        @GetMapping("/latest")
        public PetPhysicalResponse latest(
                @PathVariable Long petId,
                @AuthenticationPrincipal User user
        ) {
            petService.getPetByIdAndUser(petId, user);
            return physicalService.getLatest(petId);
        }
    }

