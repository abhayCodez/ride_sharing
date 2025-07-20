package com.abhayCodez.ridesharing.repository;

import com.abhayCodez.ridesharing.entity.Ride;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {
    List<Ride> findByUser_Id(ObjectId userId);
}
