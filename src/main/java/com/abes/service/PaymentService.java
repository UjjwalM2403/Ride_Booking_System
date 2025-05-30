package com.abes.service;

import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.enums.PaymentMethod;
import java.util.List;

public interface PaymentService {
    ResponseDto processPayment(Long rideId, PaymentMethod method) throws RideNotFoundException, PaymentException;
    Payment getPaymentById(Long id) throws PaymentException;
    Payment getPaymentByRideId(Long rideId) throws PaymentException;
    List<Payment> getAllPayments();
    ResponseDto refundPayment(Long paymentId) throws PaymentException;
}