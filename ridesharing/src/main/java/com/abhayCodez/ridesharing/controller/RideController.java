package com.abhayCodez.ridesharing.controller;

import com.abhayCodez.ridesharing.dto.*;
import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.service.AccountService;
import com.abhayCodez.ridesharing.service.RideService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/book")
    public ResponseEntity<BookRideResponse> bookRide(@RequestBody @Valid BookRideRequest request) {
        return ResponseEntity.ok(rideService.bookRide(request));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/accept")
    public ResponseEntity<BookRideResponse> acceptRide(@RequestBody @Valid AcceptRideRequest request) {
        return ResponseEntity.ok(
                rideService.acceptRide(new ObjectId(request.getRideId()), new ObjectId(request.getDriverId()))
        );
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/start")
    public ResponseEntity<BookRideResponse> startRide(@RequestBody @Valid AcceptRideRequest request) {
        return ResponseEntity.ok(
                rideService.startRide(new ObjectId(request.getRideId()), new ObjectId(request.getDriverId()))
        );
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/complete")
    public ResponseEntity<BookRideResponse> completeRide(@RequestBody @Valid AcceptRideRequest request) {
        return ResponseEntity.ok(
                rideService.completeRide(new ObjectId(request.getRideId()), new ObjectId(request.getDriverId()))
        );
    }

    @PreAuthorize("hasRole('USER') or hasRole('DRIVER')")
    @PostMapping("/cancel")
    public ResponseEntity<BookRideResponse> cancelRide(@RequestBody @Valid RideRequest request) {
        return ResponseEntity.ok(
                rideService.cancelRide(new ObjectId(request.getRideId()))
        );
    }

    @PreAuthorize("hasRole('USER') or hasRole('DRIVER') or hasRole('ADMIN')")
    @GetMapping("/byId/{rideId}")
    public ResponseEntity<BookRideResponse> getRide(@PathVariable String rideId) {
        return ResponseEntity.ok(
                rideService.getRide(new ObjectId(rideId))
        );
    }

    @PreAuthorize("hasRole('USER') or hasRole('DRIVER')")
    @PostMapping("/getAllRides")
    public ResponseEntity<List<BookRideResponse>> getAllRides(Authentication auth) {
        Account user = accountService.getByEmail(auth.getName());
        return ResponseEntity.ok(
                rideService.getAllRides(user.getId())
        );
    }
}
