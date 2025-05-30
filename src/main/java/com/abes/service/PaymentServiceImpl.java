package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.enums.*;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private PaymentDao paymentDao = new PaymentDaoImpl();
    private RideService rideService = new RideServiceImpl();
    
    @Override
    public ResponseDto processPayment(Long rideId, PaymentMethod method) throws RideNotFoundException, PaymentException {
        Ride ride = rideService.getRideById(rideId);
        
        if (ride.getStatus() != RideStatus.COMPLETED) {
            throw new PaymentException("Payment can only be processed for completed rides");
        }
        
        // Check if payment already exists
        Payment existingPayment = paymentDao.findByRideId(rideId);
        if (existingPayment != null && existingPayment.getStatus() == PaymentStatus.COMPLETED) {
            throw new PaymentException("Payment already completed for this ride");
        }
        
        Payment payment = new Payment();
        payment.setRideId(rideId);
        payment.setAmount(ride.getFare());
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);
        
        Payment savedPayment = paymentDao.save(payment);
        
        // Simulate payment processing
        try {
            // Simulate external payment gateway call
            Thread.sleep(1000);
            savedPayment.setStatus(PaymentStatus.COMPLETED);
            paymentDao.update(savedPayment);
        } catch (InterruptedException e) {
            savedPayment.setStatus(PaymentStatus.FAILED);
            paymentDao.update(savedPayment);
            throw new PaymentException("Payment processing failed");
        }
        
        return new ResponseDto(true, "Payment processed successfully", savedPayment);
    }
    
    @Override
    public Payment getPaymentById(Long id) throws PaymentException {
        Payment payment = paymentDao.findById(id);
        if (payment == null) {
            throw new PaymentException("Payment not found with ID: " + id);
        }
        return payment;
    }
    
    @Override
    public Payment getPaymentByRideId(Long rideId) throws PaymentException {
        Payment payment = paymentDao.findByRideId(rideId);
        if (payment == null) {
            throw new PaymentException("Payment not found for ride ID: " + rideId);
        }
        return payment;
    }
    
    @Override
    public List<Payment> getAllPayments() {
        return paymentDao.findAll();
    }
    
    @Override
    public ResponseDto refundPayment(Long paymentId) throws PaymentException {
        Payment payment = getPaymentById(paymentId);
        
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentException("Only completed payments can be refunded");
        }
        
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentDao.update(payment);
        
        return new ResponseDto(true, "Payment refunded successfully", payment);
    }
}
