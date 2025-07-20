package com.abhayCodez.ridesharing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    private String vehicleNumber;
    private String model;
    private String color;
    private String type; // sedan, hatchback etc.
}
