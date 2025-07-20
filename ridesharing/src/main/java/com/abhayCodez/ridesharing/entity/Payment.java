package com.abhayCodez.ridesharing.entity;

import com.abhayCodez.ridesharing.enums.PaymentMethod;
import com.abhayCodez.ridesharing.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id
    private ObjectId id;
    @DBRef
    private Ride ride;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    @CreatedDate
    private LocalDateTime transactionDate;
}