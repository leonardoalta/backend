package com.blacksystem.system.services;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.Treatment;
import com.blacksystem.system.models.dto.TreatmentRequest;
import com.blacksystem.system.models.enums.AdministrationRoute;
import com.blacksystem.system.models.enums.DosageUnit;
import com.blacksystem.system.repositorys.TreatmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final CloudinaryService cloudinaryService;

    public TreatmentService(TreatmentRepository treatmentRepository,
                            CloudinaryService cloudinaryService) {
        this.treatmentRepository = treatmentRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Treatment createTreatment(
            Pet pet,
            TreatmentRequest req,
            MultipartFile image
    ) {

        Treatment t = new Treatment();
        t.setMedicineName(req.getMedicineName());
        t.setDate(LocalDate.now());
        t.setDosageAmount(req.getDosageAmount());
        t.setDosageUnit(DosageUnit.valueOf(req.getDosageUnit().toUpperCase()));
        t.setAdministration(
                AdministrationRoute.valueOf(req.getAdministration().toUpperCase())
        );
        t.setDurationDays(req.getDurationDays());
        t.setIntervalHours(req.getIntervalHours());
        t.setWantsReminders(req.isWantsReminders());
        t.setPet(pet);

        if (image != null && !image.isEmpty()) {
            t.setImageUrl(
                    cloudinaryService.uploadImage(image, "treatments")
            );
        }

        return treatmentRepository.save(t);
    }

    public List<Treatment> getTreatmentsByPet(Pet pet) {
        return treatmentRepository.findByPet(pet);
    }
}
