package com.blacksystem.system.repositorys.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<com.blacksystem.system.models.Address, Long> {
}
