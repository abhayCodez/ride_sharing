package com.abhayCodez.ridesharing.service;

import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.entity.GeoLocation;
import com.abhayCodez.ridesharing.entity.Ride;
import com.abhayCodez.ridesharing.enums.Role;
import com.abhayCodez.ridesharing.repository.AccountRepository;
//import com.abhayCodez.ridesharing.repository.RideRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    private RideRepository rideRepository;

    // CRUD

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found."));
    }

//    public Account getById(String id) {
//        return accountRepository.findById(new ObjectId(id))
//                .orElseThrow(() -> new RuntimeException("Account not found."));
//    }

//    public List<Account> getAll() {
//        return accountRepository.findAll();
//    }

    public Account update(String email, Account updated) {
        Account account = getByEmail(email);
        if (updated.getName() != null) account.setName(updated.getName());
        if(updated.getEmail() != null) account.setEmail(updated.getEmail());
        if(updated.getPassword() != null) account.setPassword(updated.getPassword());
        if (updated.getPhone() != null) account.setPhone(updated.getPhone());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public void delete(String email) {
        Account account = getByEmail(email);
        accountRepository.delete(account);
    }

//    public void blockAccount(String email) {
//        Account account = getByEmail(email);
//        account.setRole(Role.BLOCKED_USER);
//        account.setUpdatedAt(LocalDateTime.now());
//        accountRepository.save(account);
//    }

//
//    // User-specific
//
//    public Map<String, Double> getUserLocation(String email) {
//        Account account = getByEmail(email);
//        GeoLocation location = null;
//        location = account.getHomeLocation();
//        if (location == null) {
//            throw new RuntimeException("Location not set.");
//        }
//        Map<String, Double> map = new HashMap<>();
//        map.put("latitude", location.getLatitude());
//        map.put("longitude", location.getLongitude());
//        return map;
//    }

//    public Ride cancelRide(String rideId) {
//        Ride ride = rideRepository.findById(new ObjectId(rideId))
//                .orElseThrow(() -> new RuntimeException("Ride not found"));
//        ride.setStatus("CANCELLED");
//        ride.setUpdatedAt(LocalDateTime.now());
//        return rideRepository.save(ride);
//    }
//
//    // Driver-specific
//
//    public Account updateAvailability(String email, boolean available) {
//        Account driver = getByEmail(email);
//        driver.getDriverDetails().setAvailable(available);
//        driver.setUpdatedAt(LocalDateTime.now());
//        return accountRepository.save(driver);
//    }
//
//    public Account updateDriverLocation(String email, Map<String, Double> location) {
//        Account driver = getByEmail(email);
//        driver.getDriverDetails().setLocation(location);
//        driver.setUpdatedAt(LocalDateTime.now());
//        return accountRepository.save(driver);
//    }
//
//    public Ride acceptRide(String email, String rideId) {
//        Ride ride = rideRepository.findById(new ObjectId(rideId))
//                .orElseThrow(() -> new RuntimeException("Ride not found"));
//        ride.setDriverEmail(email);
//        ride.setStatus("ACCEPTED");
//        ride.setUpdatedAt(LocalDateTime.now());
//        return rideRepository.save(ride);
//    }
//
//    public Ride completeRide(String rideId) {
//        Ride ride = rideRepository.findById(new ObjectId(rideId))
//                .orElseThrow(() -> new RuntimeException("Ride not found"));
//        ride.setStatus("COMPLETED");
//        ride.setUpdatedAt(LocalDateTime.now());
//        return rideRepository.save(ride);
//    }
//
//    // Admin-specific
//
//    public List<Ride> getAllRides() {
//        return rideRepository.findAll();
//    }
//
}
