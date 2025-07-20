package com.abhayCodez.ridesharing.repository;

import com.abhayCodez.ridesharing.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Optional<Account> findByEmail(String email);
}
