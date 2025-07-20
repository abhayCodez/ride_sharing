package com.abhayCodez.ridesharing.entity;

import com.abhayCodez.ridesharing.enums.PaymentStatus;
import com.abhayCodez.ridesharing.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rides")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ride {
    @Id
    private ObjectId id;
    @DBRef
    private Account user;
    @DBRef
    private Account driver;
    private GeoLocation pickupLocation;
    private GeoLocation dropLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RideStatus status;
    private double fare;
    private PaymentStatus paymentStatus;
}