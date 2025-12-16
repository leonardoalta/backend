package com.blacksystem.system.repositorys.vaccines;

import com.blacksystem.system.models.AnnualVaccine;
import com.blacksystem.system.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnnualVaccineRepository
        extends JpaRepository<AnnualVaccine, Long> {

    List<AnnualVaccine> findByPet(Pet pet);
    List<AnnualVaccine> findByWantsRemindersTrueAndNextVaccineDate(LocalDate date);
    List<AnnualVaccine> findByPetAndWantsRemindersTrueAndNextVaccineDateBetween(
            Pet pet,
            LocalDate start,
            LocalDate end
    );

}
