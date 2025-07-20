package com.abhayCodez.ridesharing.controller;

import com.abhayCodez.ridesharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/location")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserLocation(Authentication auth) {
        return ResponseEntity.ok(userService.getUserLocation(auth.getName()));
    }
}
