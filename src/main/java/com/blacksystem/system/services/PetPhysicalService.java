package com.blacksystem.system.services;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.PetPhysicalData;
import com.blacksystem.system.models.dto.PhysicalDataRequest;
import com.blacksystem.system.models.enums.BodyConditionScore;
import com.blacksystem.system.repositorys.PetPhysicalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service
@Transactional

public class PetPhysicalService {

    private final PetPhysicalRepository repository;

    public PetPhysicalService(PetPhysicalRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PetPhysicalData save(Pet pet, PhysicalDataRequest req) {

        System.out.println("🔥 GUARDANDO CONTROL FÍSICO PARA PET ID: " + pet.getId());
        System.out.println("BCS = " + req.getBcs());

        PetPhysicalData data = new PetPhysicalData();
        data.setPet(pet);

        data.setWeightKg(
                req.getWeightKg() != null ? req.getWeightKg() : pet.getWeight()
        );

        data.setHeightCm(req.getHeightCm());
        data.setBcs(BodyConditionScore.fromValue(req.getBcs()));
        data.setRecordedAt(LocalDate.parse(req.getRecordedAt()));

        return repository.save(data);
    }


    public List<PetPhysicalData> getHistory(Pet pet) {
        return repository.findByPetOrderByRecordedAtDesc(pet);
    }

    public PetPhysicalData getLatest(Pet pet) {
        return repository
                .findTopByPetOrderByRecordedAtDesc(pet)
                .orElse(null);
    }
}
