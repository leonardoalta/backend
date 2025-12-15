package com.blacksystem.system.services;

import com.blacksystem.system.models.Address;
import com.blacksystem.system.models.Role;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.AuthResponse;
import com.blacksystem.system.models.dto.LoginRequest;
import com.blacksystem.system.models.dto.RegisterRequest;
import com.blacksystem.system.repositorys.email.VerificationCodeRepository;
import com.blacksystem.system.repositorys.users.AddressRepository;
import com.blacksystem.system.repositorys.users.RoleRepository;
import com.blacksystem.system.repositorys.users.UserRepository;
import com.blacksystem.system.security.JwtUtil;
import com.blacksystem.system.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import com.blacksystem.system.models.email.VerificationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Random;



@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private EmailService emailService;


    public AuthResponse register(RegisterRequest registerRequest) {

        logger.info("Iniciando registro para: {}", registerRequest.getEmail());

        validateRegistration(registerRequest);

        User user = createUserFromRequest(registerRequest);

        Role userRole = getOrCreateUserRole();
        user.setRolesUser(new HashSet<>(Set.of(userRole)));

        logger.debug("User antes de guardar: {}", user);

        User savedUser = userRepository.save(user);
        logger.info("Usuario guardado: {} (ID: {})", savedUser.getEmailUser(), savedUser.getIdUser());

        String code = generateNumericOTP(6);
        saveVerificationCode(savedUser, code);

        emailService.sendVerificationCode(savedUser.getEmailUser(), code);
        logger.info("Código de verificación enviado a: {}", savedUser.getEmailUser());

        String token = jwtUtil.generateToken(savedUser);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(savedUser.getEmailUser());
        response.setNameUser(savedUser.getNameUser());

        return response;
    }

    private void validateRegistration(RegisterRequest registerRequest) {

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            logger.warn("Passwords no coinciden");
            throw new RuntimeException("Passwords do not match");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!registerRequest.getEmail().matches(emailRegex)) {
            throw new RuntimeException("Email format is invalid");
        }

        if (userRepository.existsByEmailUser(registerRequest.getEmail())) {
            logger.warn("Email ya existe: {}", registerRequest.getEmail());
            throw new RuntimeException("Email is already in use");
        }


        if (registerRequest.getPhoneNumber() != null && !registerRequest.getPhoneNumber().isEmpty()) {
            if (userRepository.existsByPhoneUser(registerRequest.getPhoneNumber())) {
                logger.warn("Teléfono ya existe: {}", registerRequest.getPhoneNumber());
                throw new RuntimeException("Phone number is already in use");
            }
        }
    }

    private User createUserFromRequest(RegisterRequest registerRequest) {
        User user = new User();
        user.setNameUser(registerRequest.getUserName());
        user.setLastNameUser(registerRequest.getLastName());
        user.setMiddleNameUser(registerRequest.getMiddleName());
        user.setEmailUser(registerRequest.getEmail());
        user.setPhoneUser(registerRequest.getPhoneNumber());
        user.setPasswordUser(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabledUser(false);


        if (registerRequest.getAddress() != null) {
            Address address = new Address();
            address.setNameStreetUser(registerRequest.getAddress().getNameStreetUser());
            address.setNumberHouseUser(registerRequest.getAddress().getNumberHouseUser());
            address.setNameDistrictUser(registerRequest.getAddress().getNameDistrictUser());
            address.setZipCodeUser(registerRequest.getAddress().getZipCodeUser());
            user.setAddressUser(address);
        }

        return user;
    }

    private Role getOrCreateUserRole() {
        return roleRepository.findByNameRole("ROLE_USER")
                .orElseGet(() -> {
                    logger.info("Creando rol ROLE_USER");
                    Role newRole = new Role();
                    newRole.setNameRole("ROLE_USER");
                    newRole.setDescriptionRole("User normal");
                    return roleRepository.save(newRole);
                });
    }

    private void saveVerificationCode(User user, String code) {
        VerificationCode verification = new VerificationCode();
        verification.setUser(user);
        verification.setCode(code);
        verification.setExpiryAt(LocalDateTime.now().plusMinutes(10));
        verification.setAttempts(0);
        verificationCodeRepository.save(verification);

        logger.debug("Código de verificación guardado: {}", code);
    }

    private String generateNumericOTP(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByEmailUser(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User no found"));
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.getEmailUser());
        response.setNameUser(user.getNameUser());
        return response;
    }

    public User findWhithEmaill(String email) {
        return userRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}