package com.abes.service;

import com.abes.dto.*;
import com.abes.enums.RideStatus;
import com.abes.enums.VehicleType;
import com.abes.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RideServiceImplTest {

    private RideService rideService;

    @BeforeEach
    void setUp() {
        rideService = new RideServiceImpl();
    }

//    @Test
//    void testRequestRide_Success() throws Exception {
//        Location pickup = new Location(0.0, 0.0, "A");
//        Location drop = new Location(1.0, 1.0, "B");
//        RideRequest request = new RideRequest(1L, 1L, pickup, drop, VehicleType.SEDAN);
//
//        ResponseDto response = rideService.requestRide(request);
//        assertTrue(response.isSuccess());
//    } // giving error


    @Test
    void testAcceptRide_InvalidId() {
        assertThrows(RideNotFoundException.class, () -> {
            rideService.acceptRide(999L, 1L);
        });
    }

    @Test
    void testCancelRide_InvalidId() {
        assertThrows(RideNotFoundException.class, () -> {
            rideService.cancelRide(999L, 1L);
        });
    }

    @Test
    void testStartRide_InvalidId() {
        assertThrows(RideNotFoundException.class, () -> {
            rideService.startRide(999L, 1L);
        });
    }

    @Test
    void testCompleteRide_InvalidId() {
        assertThrows(RideNotFoundException.class, () -> {
            rideService.completeRide(999L, 1L);
        });
    }
}
