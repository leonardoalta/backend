package com.blacksystem.system.repositorys;

import com.blacksystem.system.models.VetVisit;
import com.blacksystem.system.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VetVisitRepository extends JpaRepository<VetVisit, Long> {

    List<VetVisit> findByPet(Pet pet);

    Optional<VetVisit> findByIdAndPet(Long id, Pet pet);
    List<VetVisit> findByWantsRemindersTrueAndNextVisitDate(LocalDate date);

}
