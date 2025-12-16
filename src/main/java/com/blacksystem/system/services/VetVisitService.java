package com.blacksystem.system.services;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.VetVisit;
import com.blacksystem.system.models.dto.VetVisitRequest;
import com.blacksystem.system.repositorys.VetVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VetVisitService {

    private final VetVisitRepository vetVisitRepository;
    private final CloudinaryService cloudinaryService;

    public VetVisitService(VetVisitRepository vetVisitRepository,
                           CloudinaryService cloudinaryService) {
        this.vetVisitRepository = vetVisitRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // --------------------------------------------------
    // 🔥 CREAR VISITA
    // --------------------------------------------------
    public VetVisit createVisit(
            Pet pet,
            VetVisitRequest req,
            MultipartFile photo
    ) {

        VetVisit visit = new VetVisit();
        visit.setVisitDate(req.getVisitDate());
        visit.setReason(req.getReason());
        visit.setDiagnosis(req.getDiagnosis());
        visit.setTreatment(req.getTreatment());
        visit.setNotes(req.getNotes());
        visit.setVetName(req.getVetName());
        visit.setClinic(req.getClinic());
        visit.setWantsReminders(req.isWantsReminders());
        visit.setNextVisitDate(req.getNextVisitDate());
        visit.setPet(pet);

        if (photo != null && !photo.isEmpty()) {
            visit.setPhotoUrl(
                    cloudinaryService.uploadImage(photo, "vet_visits")
            );
        }

        return vetVisitRepository.save(visit);
    }

    // --------------------------------------------------
    // 🔥 ACTUALIZAR VISITA
    // --------------------------------------------------
    public VetVisit updateVisit(
            Pet pet,
            Long id,
            VetVisitRequest req,
            MultipartFile photo
    ) {
        VetVisit visit = vetVisitRepository
                .findByIdAndPet(id, pet)
                .orElseThrow(() ->
                        new RuntimeException("Visita no encontrada")
                );

        visit.setVisitDate(req.getVisitDate());
        visit.setReason(req.getReason());
        visit.setDiagnosis(req.getDiagnosis());
        visit.setTreatment(req.getTreatment());
        visit.setNotes(req.getNotes());
        visit.setVetName(req.getVetName());
        visit.setClinic(req.getClinic());
        visit.setWantsReminders(req.isWantsReminders());
        visit.setNextVisitDate(req.getNextVisitDate());

        if (photo != null && !photo.isEmpty()) {
            visit.setPhotoUrl(
                    cloudinaryService.uploadImage(photo, "vet_visits")
            );
        }

        return vetVisitRepository.save(visit);
    }

    // --------------------------------------------------
    // 🔥 LISTAR VISITAS
    // --------------------------------------------------
    public List<VetVisit> getVisitsByPet(Pet pet) {
        return vetVisitRepository.findByPet(pet);
    }
}
