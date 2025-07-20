package com.abhayCodez.ridesharing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "analytics")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Analytics {
    @Id
    private ObjectId id;
    private int totalRides;
    private double revenueGenerated;
    private double averageRating;
    private String city;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}