package com.abhayCodez.ridesharing.controller;

import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.entity.Ride;
import com.abhayCodez.ridesharing.repository.AccountRepository;
import com.abhayCodez.ridesharing.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        return ResponseEntity.ok(accountService.getByEmail(auth.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMyAccount(@RequestBody Account updated, Authentication auth) {
        return ResponseEntity.ok(accountService.update(auth.getName(), updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyAccount(Authentication auth) {
        accountService.delete(auth.getName());
        return ResponseEntity.ok("Account deleted successfully.");
    }

//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<Account>> getAllAccounts() {
//        return ResponseEntity.ok(accountService.getAll());
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/me/id")
//    public ResponseEntity<?> getMyId(Authentication auth) {
//        String email = auth.getName();
//        Account account = accountService.getByEmail(email);
//        return ResponseEntity.ok(account.getId());
//    }
//
//    @PostMapping("/admin/block-user/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> blockUser(Authentication auth) {
//        String email = auth.getName();
//        accountService.blockAccount(email);
//        return ResponseEntity.ok("User blocked.");
//    }
//
//    @PostMapping("/admin/block-driver/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> blockDriver(Authentication auth) {
//        String email = auth.getName();
//        accountService.blockAccount(email);
//        return ResponseEntity.ok("Driver blocked.");
//    }
//
//    @GetMapping("/admin/health")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> health() {
//        return ResponseEntity.ok("Service is healthy!");
//    }

//    @GetMapping("/user/location")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> getUserLocation(Authentication auth) {
//        return ResponseEntity.ok(accountService.getUserLocation(auth.getName()));
//    }
}
