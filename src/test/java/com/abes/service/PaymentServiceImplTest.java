package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.enums.PaymentStatus;
import com.abes.enums.VehicleType;
import com.abes.exception.*;
import com.abes.service.*;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceImplTest {

    PaymentDao simpleDao = new PaymentDao() {
        Map<Long, Payment> db = new HashMap<>();
        {
            Payment p = new Payment();
            p.setRideId(100L);
            p.setAmount(100.0);
            p.setStatus(PaymentStatus.COMPLETED);
            db.put(1L, p); // ID 1L maps to Ride ID 100L
        }

        public Payment findById(Long id) { return db.get(id); }
        public Payment update(Payment p) { db.put(p.getRideId(), p); return p; }
        public Payment save(Payment p) { db.put(p.getRideId(), p); return p; }
        public Payment findByRideId(Long id) { return null; }
        public List<Payment> findAll() { return new ArrayList<>(db.values()); }
        public boolean delete(Long id) { return db.remove(id) != null; }
    };

    RideService dummyRideService = new RideService() {
        public Ride getRideById(Long id) { return new Ride(); }
        public ResponseDto requestRide(RideRequest rideRequest) { return null; }
        public ResponseDto acceptRide(Long rideId, Long driverId) { return null; }
        public ResponseDto startRide(Long rideId, Long driverId) { return null; }
        public ResponseDto completeRide(Long rideId, Long driverId) { return null; }
        public ResponseDto cancelRide(Long rideId, Long userId) { return null; }
        public ResponseDto cancelRideByDriver(Long rideId, Long driverId) { return null; }
        public List<Ride> getRidesByUserId(Long userId) { return null; }
        public List<Ride> getRidesByDriverId(Long driverId) { return null; }
        public List<Ride> getAllRides() { return null; }
        public double calculateFare(Location pickup, Location drop, VehicleType vehicleType) { return 0; }
    };

    PaymentServiceImpl service = new PaymentServiceImpl(simpleDao, dummyRideService);

    @Test
    void testRefundPayment_success() throws PaymentException {
        ResponseDto res = service.refundPayment(1L);
        assertTrue(res.isSuccess());
        assertEquals("Payment refunded successfully", res.getMessage());
    }

    @Test
    void testRefundPayment_invalidStatus() {
        Payment p = new Payment();
        p.setRideId(2L);
        p.setStatus(PaymentStatus.PENDING);
        simpleDao.save(p);

        assertThrows(PaymentException.class, () -> service.refundPayment(2L));
    }

    @Test
    void testGetPaymentById_notFound() {
        assertThrows(PaymentException.class, () -> service.getPaymentById(99L));
    }

    @Test
    void testGetAllPayments() {
        assertNotNull(service.getAllPayments());
    }
}

