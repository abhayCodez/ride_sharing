package com.abhayCodez.ridesharing.service;

import com.abhayCodez.ridesharing.dto.BookRideRequest;
import com.abhayCodez.ridesharing.dto.BookRideResponse;
import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.entity.GeoLocation;
import com.abhayCodez.ridesharing.entity.Ride;
import com.abhayCodez.ridesharing.enums.PaymentStatus;
import com.abhayCodez.ridesharing.enums.RideStatus;
import com.abhayCodez.ridesharing.repository.AccountRepository;
import com.abhayCodez.ridesharing.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private AccountRepository accountRepository;

    public BookRideResponse bookRide(BookRideRequest request) {
        ObjectId userId = new ObjectId(request.getUserId());
        Account user = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ride ride = new Ride();
        ride.setUser(user);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus(RideStatus.PENDING);
        ride.setFare(estimateFare(request.getPickupLocation(), request.getDropLocation()));
        ride.setPaymentStatus(PaymentStatus.UNPAID);
        ride = rideRepository.save(ride);

        user.getRides().add(ride);
        accountRepository.save(user);

        return result(ride);
    }

    public BookRideResponse acceptRide(ObjectId rideId, ObjectId driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Account driver = accountRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);

        driver.getRides().add(ride);
        accountRepository.save(driver);

        return result(ride);
    }

    public BookRideResponse startRide(ObjectId rideId, ObjectId driverId) {
        Ride ride = getRideById(rideId);
        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
        return result(ride);
    }

    public BookRideResponse completeRide(ObjectId rideId, ObjectId driverId) {
        Ride ride = getRideById(rideId);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        ride.setPaymentStatus(PaymentStatus.PAID);
        rideRepository.save(ride);
        return result(ride);
    }

    public BookRideResponse cancelRide(ObjectId rideId) {
        Ride ride = getRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
        return result(ride);
    }

    public BookRideResponse getRide(ObjectId rideId) throws AccessDeniedException {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (hasRole("ADMIN")) {
            return result(ride);
        }

        if (hasRole("USER")) {
            if (!ride.getUser().getEmail().equals(loggedInEmail)) {
                throw new AccessDeniedException("This ride does not belong to you!");
            }
            return result(ride);
        }

        if (hasRole("DRIVER")) {
            if (ride.getDriver() == null || !ride.getDriver().getEmail().equals(loggedInEmail)) {
                throw new AccessDeniedException("This ride is not assigned to you!");
            }
            return result(ride);
        }
        throw new AccessDeniedException("Unauthorized access.");
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public List<BookRideResponse> getAllRides(ObjectId userId) {
        List<Ride> rides = rideRepository.findByUser_Id(userId);
        return rides.stream().map(this::result).collect(Collectors.toList());
    }

    public long getTotalRides() {
        return rideRepository.count();
    }


    private Ride getRideById(ObjectId rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
    }

    private BookRideResponse result(Ride ride) {
        BookRideResponse response = new BookRideResponse();
        response.setRideId(String.valueOf(ride.getId()));
        response.setUserId(ride.getUser() != null ? String.valueOf(ride.getUser().getId()) : null);
        response.setDriverId(ride.getDriver() != null ? String.valueOf(ride.getDriver().getId()) : null);
        response.setPickupLocation(ride.getPickupLocation());
        response.setDropLocation(ride.getDropLocation());
        response.setStartTime(ride.getStartTime());
        response.setEndTime(ride.getEndTime());
        response.setStatus(ride.getStatus());
        response.setFare(ride.getFare());
        response.setPaymentStatus(ride.getPaymentStatus());
        return response;
    }

    private double estimateFare(GeoLocation pickup, GeoLocation drop) {
        double distance = haversine(pickup.getLatitude(), pickup.getLongitude(),
                drop.getLatitude(), drop.getLongitude());
        return distance * 10;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
