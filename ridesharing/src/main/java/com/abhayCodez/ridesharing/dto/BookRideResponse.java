package com.abhayCodez.ridesharing.dto;

import com.abhayCodez.ridesharing.enums.PaymentStatus;
import com.abhayCodez.ridesharing.enums.RideStatus;
import com.abhayCodez.ridesharing.entity.GeoLocation;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
public class BookRideResponse {
    private String rideId;
    private String userId;
    private String driverId;
    private GeoLocation pickupLocation;
    private GeoLocation dropLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RideStatus status;
    private double fare;
    private PaymentStatus paymentStatus;
}
