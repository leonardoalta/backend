package com.blacksystem.system.repositorys.pets;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner(User owner);
}
