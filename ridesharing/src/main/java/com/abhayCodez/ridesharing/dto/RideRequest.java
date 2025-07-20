package com.abhayCodez.ridesharing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RideRequest {
    @NotBlank(message = "rideId cannot be blank")
    private String rideId;
}
