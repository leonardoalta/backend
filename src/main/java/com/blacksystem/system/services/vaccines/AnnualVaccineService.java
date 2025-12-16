package com.blacksystem.system.services.vaccines;

import com.blacksystem.system.models.AnnualVaccine;
import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.dto.AnnualVaccineRequest;
import com.blacksystem.system.repositorys.vaccines.AnnualVaccineRepository;
import com.blacksystem.system.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnnualVaccineService {

    private final AnnualVaccineRepository repository;
    private final CloudinaryService cloudinaryService;

    public AnnualVaccineService(
            AnnualVaccineRepository repository,
            CloudinaryService cloudinaryService
    ) {
        this.repository = repository;
        this.cloudinaryService = cloudinaryService;
    }

    // =========================================================
    // 🟢 CREAR VACUNA
    // =========================================================
    public AnnualVaccine create(
            Pet pet,
            AnnualVaccineRequest req,
            MultipartFile photo
    ) {
        AnnualVaccine vaccine = new AnnualVaccine();

        applyData(vaccine, req);
        vaccine.setPet(pet);

        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo, "vaccines");
            vaccine.setPhotoUrl(imageUrl);
        }

        return repository.save(vaccine);
    }

    // =========================================================
    // 🟡 ACTUALIZAR VACUNA
    // =========================================================
    public AnnualVaccine update(
            AnnualVaccine vaccine,
            AnnualVaccineRequest req,
            MultipartFile photo
    ) {
        applyData(vaccine, req);

        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo, "vaccines");
            vaccine.setPhotoUrl(imageUrl);
        }

        return repository.save(vaccine);
    }

    // =========================================================
    // 🔵 LISTAR VACUNAS POR MASCOTA
    // =========================================================
    public List<AnnualVaccine> listByPet(Pet pet) {
        return repository.findByPet(pet);
    }

    // =========================================================
    // 🔐 OBTENER VACUNA POR ID + PET (SEGURIDAD)
    // =========================================================
    public AnnualVaccine getByIdAndPet(Long id, Pet pet) {
        return repository.findById(id)
                .filter(v -> v.getPet().getId().equals(pet.getId()))
                .orElseThrow(() ->
                        new RuntimeException("Vacuna no encontrada para esta mascota")
                );
    }

    // =========================================================
    // 🧠 MÉTODO CENTRAL DE MAPEO (NO DUPLICAR LÓGICA)
    // =========================================================
    private void applyData(AnnualVaccine vaccine, AnnualVaccineRequest req) {

        vaccine.setName(req.getName());
        vaccine.setDate(LocalDate.parse(req.getDate()));
        vaccine.setManufactureDate(LocalDate.parse(req.getManufactureDate()));
        vaccine.setLotNumber(req.getLotNumber());
        vaccine.setVetName(req.getVetName());

        vaccine.setWantsReminders(req.isWantsReminders());

        if (req.isWantsReminders() && req.getNextVaccineDate() != null) {
            vaccine.setNextVaccineDate(
                    LocalDate.parse(req.getNextVaccineDate())
            );
        } else {
            vaccine.setNextVaccineDate(null);
        }
    }
}
