package com.abhayCodez.ridesharing.entity;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rating {
    @Id
    private ObjectId id;
    @DBRef
    private ObjectId rideId;
    private ObjectId userId;
    private ObjectId driverId;
    @DecimalMin(value = "1.0", message = "Rating must be >= 1.0")
    @DecimalMax(value = "5.0", message = "Rating must be <= 5.0")
    private double rating;
    @NotBlank
    private String comment;
    private LocalDateTime createdAt;
}