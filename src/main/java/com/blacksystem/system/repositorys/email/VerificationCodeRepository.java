package com.blacksystem.system.repositorys.email;

import com.blacksystem.system.models.email.VerificationCode;
import com.blacksystem.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByUser(User user);

    Optional<VerificationCode> findByCode(String code);

    @Transactional
    void deleteByUser(User user);

    @Transactional
    void deleteByExpiryAtBefore(LocalDateTime dateTime);
}