package com.abes.dao;

import com.abes.dto.Payment;
import java.util.List;

public interface PaymentDao {
    Payment save(Payment payment);
    Payment findById(Long id);
    Payment findByRideId(Long rideId);
    List<Payment> findAll();
    Payment update(Payment payment);
    boolean delete(Long id);
}