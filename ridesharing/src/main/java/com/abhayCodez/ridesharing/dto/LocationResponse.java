package com.abhayCodez.ridesharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponse {
    private double latitude;
    private double longitude;
}
