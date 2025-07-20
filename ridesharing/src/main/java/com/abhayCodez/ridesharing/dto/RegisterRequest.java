package com.abhayCodez.ridesharing.dto;

import com.abhayCodez.ridesharing.entity.GeoLocation;
import com.abhayCodez.ridesharing.entity.Vehicle;
import com.abhayCodez.ridesharing.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is invalid")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "Role must be specified")
    private Role role;

    // USER specific
    private GeoLocation homeLocation;

    // DRIVER specific
    private String licenseNumber;
    private GeoLocation currentLocation;
    private Vehicle vehicle;

    // ADMIN specific
    private String adminCode;
}