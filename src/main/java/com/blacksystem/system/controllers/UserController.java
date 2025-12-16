package com.blacksystem.system.controllers;

import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.AuthResponse;
import com.blacksystem.system.models.dto.LoginRequest;
import com.blacksystem.system.models.dto.RegisterRequest;
import com.blacksystem.system.models.dto.UserProfileDTO;
import com.blacksystem.system.models.email.VerificationCode;
import com.blacksystem.system.repositorys.email.VerificationCodeRepository;
import com.blacksystem.system.services.AuthService;
import com.blacksystem.system.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.security.core.Authentication;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error register: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error login: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        try {
            logger.info("Verificando código para: {}", email);

            User user = authService.findWhithEmaill(email);

            VerificationCode verification = verificationCodeRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Verification code not found"));

            if (verification.getExpiryAt().isBefore(LocalDateTime.now())) {
                logger.warn("Código expirado para: {}", email);
                return ResponseEntity.badRequest().body(Map.of("error", "Code expired"));
            }

            if (!verification.getCode().equals(code)) {
                verification.setAttempts(verification.getAttempts() + 1);
                verificationCodeRepository.save(verification);

                logger.warn("Código inválido para: {} (intentos: {})", email, verification.getAttempts());

                if (verification.getAttempts() >= 5) {
                    verificationCodeRepository.delete(verification);
                    return ResponseEntity.badRequest().body(Map.of("error", "Too many attempts. Request a new code."));
                }

                return ResponseEntity.badRequest().body(Map.of("error", "Invalid code"));
            }

            user.setEnabledUser(true);
            authService.saveUser(user);

            verificationCodeRepository.delete(verification);

            logger.info("Usuario verificado: {}", email);
            return ResponseEntity.ok(Map.of("message", "User verified successfully"));

        } catch (Exception e) {
            logger.error("Error en verificación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestParam String email) {
        try {
            logger.info("Reenviando código a: {}", email);

            User user = authService.findWhithEmaill(email);

            if (user.isEnabledUser()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User already verified"));
            }

            verificationCodeRepository.deleteByUser(user);

            String newCode = generateNumericOTP(6);
            VerificationCode verification = new VerificationCode();
            verification.setUser(user);
            verification.setCode(newCode);
            verification.setExpiryAt(LocalDateTime.now().plusMinutes(10));
            verification.setAttempts(0);
            verificationCodeRepository.save(verification);

            emailService.sendVerificationCode(user.getEmailUser(), newCode);

            logger.info("Código reenviado a: {}", email);
            return ResponseEntity.ok(Map.of("message", "Code resent successfully"));

        } catch (Exception e) {
            logger.error("Error reenviando código: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null
                || auth.getPrincipal() == null
                || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }

        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(
                UserProfileDTO.from(user)
        );
    }



    private String generateNumericOTP(int length) {
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


}
