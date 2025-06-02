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
