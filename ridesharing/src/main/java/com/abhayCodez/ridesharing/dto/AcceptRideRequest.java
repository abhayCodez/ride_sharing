package com.abhayCodez.ridesharing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class AcceptRideRequest {

    @NotBlank(message = "rideId cannot be empty")
    private String rideId;

    @NotBlank(message = "driverId cannot be empty")
    private String driverId;
}
