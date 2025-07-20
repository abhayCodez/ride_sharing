package com.abhayCodez.ridesharing.dto;

import com.abhayCodez.ridesharing.entity.GeoLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class BookRideRequest {

    @NotBlank(message = "UserId cannot be blank")
    private String userId;
    @NotNull(message = "Pickup location cannot be null")
    private GeoLocation pickupLocation;
    @NotNull(message = "Drop location cannot be null")
    private GeoLocation dropLocation;
}
