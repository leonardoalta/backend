package com.blacksystem.system.repositorys;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.PetPhysicalData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetPhysicalRepository
        extends JpaRepository<PetPhysicalData, Long> {

    List<PetPhysicalData> findByPetOrderByRecordedAtDesc(Pet pet);

    Optional<PetPhysicalData> findTopByPetOrderByRecordedAtDesc(Pet pet);
}
