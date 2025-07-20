package com.abhayCodez.ridesharing.entity;

import com.abhayCodez.ridesharing.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    private ObjectId id;

    private String name;
    private String email;
    private String phone;
    private String city;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef
    private List<Ride> rides;

    // USER specific fields
    private GeoLocation homeLocation;
    private String address;

    // DRIVER specific fields
    private String licenseNumber;
    private boolean isAvailable;
    private Double rating;
    private GeoLocation currentLocation;
    private Vehicle vehicle;

    // ADMIN specific fields
    private String adminCode;

}