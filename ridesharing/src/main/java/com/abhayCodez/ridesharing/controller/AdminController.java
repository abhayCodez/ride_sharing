package com.abhayCodez.ridesharing.controller;

import com.abhayCodez.ridesharing.dto.BlockAccountRequest;
import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.service.AccountService;
import com.abhayCodez.ridesharing.service.AdminService;
import com.abhayCodez.ridesharing.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RideService rideService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(adminService.getAll());
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/me/id")
//    public ResponseEntity<?> getById(Authentication auth) {
//        String email = auth.getName();
//        Account account = adminService.getByEmail(email);
//        return ResponseEntity.ok(account.getId());
//    }

    @PostMapping("/admin/block-user/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockUser(@RequestBody @Valid BlockAccountRequest request) {
        String email = request.getEmail();
        adminService.blockAccount(email);
        return ResponseEntity.ok("User blocked.");
    }

    @PostMapping("/admin/block-driver/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockDriver(@RequestBody @Valid BlockAccountRequest request) {
        String email = request.getEmail();
        adminService.blockAccount(email);
        return ResponseEntity.ok("Driver blocked.");
    }

    @GetMapping("/total-rides")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTotalRides() {
        long totalRides = rideService.getTotalRides();
        return ResponseEntity.ok(totalRides);
    }

    @GetMapping("/admin/health")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("Service is healthy!");
    }
}
