package com.abhayCodez.ridesharing.service;

import com.abhayCodez.ridesharing.dto.LocationResponse;
import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.entity.GeoLocation;
import com.abhayCodez.ridesharing.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public LocationResponse getUserLocation(String email) {
        Account account = accountService.getByEmail(email);
        GeoLocation location = account.getHomeLocation();
        if (location == null) {
            throw new RuntimeException("User home location not set.");
        }
        return new LocationResponse(location.getLatitude(), location.getLongitude());
    }

}
