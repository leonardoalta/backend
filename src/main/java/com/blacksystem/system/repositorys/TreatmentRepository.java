package com.blacksystem.system.repositorys;

import com.blacksystem.system.models.Treatment;
import com.blacksystem.system.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByWantsRemindersTrue();

    List<Treatment> findByPet(Pet pet);

    // 🔥 FILTROS POTENTES
    List<Treatment> findByDosageUnit(Enum dosageUnit);
    List<Treatment> findByAdministration(Enum administration);
}
