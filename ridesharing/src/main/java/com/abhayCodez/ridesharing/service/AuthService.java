package com.abhayCodez.ridesharing.service;

import com.abhayCodez.ridesharing.dto.AuthResponse;
import com.abhayCodez.ridesharing.dto.LoginRequest;
import com.abhayCodez.ridesharing.dto.RegisterRequest;
import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.enums.Role;
import com.abhayCodez.ridesharing.repository.AccountRepository;
import com.abhayCodez.ridesharing.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("An account already exists with this email: " + request.getEmail());
        }

        // ✅ Password strength check
        validatePasswordStrength(request.getPassword());
        validateRegisterRequest(request);
        String roleStr = String.valueOf(request.getRole());
        if (roleStr == null) {
            throw new RuntimeException("Role is required.");
        }

        Role role;
        try {
            role = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleStr);
        }

        if (role == Role.ADMIN) {
            throw new RuntimeException("Admin registration is not allowed via public API.");
        }

        if (role == Role.USER || role == Role.DRIVER) {
            Account account = Account.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .homeLocation(request.getHomeLocation())
                    .licenseNumber(request.getLicenseNumber())
                    .currentLocation(request.getCurrentLocation())
                    .vehicle(request.getVehicle())
                    .adminCode(request.getAdminCode())
                    .isAvailable(request.getRole() == Role.DRIVER ? true : false)
                    .rating(request.getRole() == Role.DRIVER ? 5.0 : null)
                    .build();

            accountRepository.save(account);

            String token = jwtUtil.generateToken(account.getEmail(), account.getRole().name());
            return new AuthResponse(token, account.getRole().name(), account.getEmail());
        }
        else {
            throw new RuntimeException("Unsupported role: " + role);
        }
    }

    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new RuntimeException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new RuntimeException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[@#$%^&+=!].*")) {
            throw new RuntimeException("Password must contain at least one special character (@#$%^&+=!).");
        }
    }

    public AuthResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getRole() == Role.BLOCKED_USER) {
            throw new RuntimeException("Your account is blocked by Admin.");
        }

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(account.getEmail(), account.getRole().name());
        return new AuthResponse(token, account.getRole().name(), account.getEmail());
    }

    private void validateRegisterRequest(RegisterRequest request) {
        switch (request.getRole()) {
            case DRIVER -> {
                if (request.getLicenseNumber() == null || request.getVehicle() == null) {
                    throw new IllegalArgumentException("Driver license and vehicle info required!");
                }
            }
            case USER -> {
                if (request.getHomeLocation() == null) {
                    throw new IllegalArgumentException("User home location required!");
                }
            }
//            case ADMIN -> {
//                if (request.getAdminCode() == null) {
//                    throw new IllegalArgumentException("Admin code required!");
//                }
//            }
        }
    }
}