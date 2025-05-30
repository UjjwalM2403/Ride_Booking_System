package com.abes.dao;

import com.abes.dto.Payment;
import java.util.*;

public class PaymentDaoImpl implements PaymentDao {
    private static Map<Long, Payment> payments = new HashMap<>();
    private static Long nextId = 1L;
    
    @Override
    public Payment save(Payment payment) {
        payment.setPaymentId(nextId++);
        payments.put(payment.getPaymentId(), payment);
        return payment;
    }
    
    @Override
    public Payment findById(Long id) {
        return payments.get(id);
    }
    
    @Override
    public Payment findByRideId(Long rideId) {
        return payments.values().stream()
                .filter(payment -> payment.getRideId().equals(rideId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
    
    @Override
    public Payment update(Payment payment) {
        if (payments.containsKey(payment.getPaymentId())) {
            payments.put(payment.getPaymentId(), payment);
            return payment;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return payments.remove(id) != null;
    }
}
