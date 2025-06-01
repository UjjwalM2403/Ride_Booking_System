package com.abes.service;

import com.abes.dto.*;
import com.abes.enums.RideStatus;
import com.abes.enums.VehicleType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceImplTest {

    private RatingServiceImpl ratingService;

    @BeforeEach
    void setup() {
        ratingService = new RatingServiceImpl();

        // Inline RideService stub for testing
        RideService testRideService = new RideService() {
            @Override
            public Ride getRideById(Long id) {
                return new Ride(id, 1L, 1L, RideStatus.COMPLETED);
            }

            // All other methods are unused in these tests
            public List<Ride> getAllRides() { return null; }
            public Ride bookRide(Ride ride) { return null; }
            public List<Ride> getRidesByUserId(Long userId) { return null; }
            public List<Ride> getRidesByDriverId(Long driverId) { return null; }
            public void cancelRide(Long id) {}
            public void completeRide(Long id) {}

            public ResponseDto requestRide(RideRequest rideRequest) { return null; }
            public ResponseDto acceptRide(Long rideId, Long driverId) { return null; }
            public ResponseDto startRide(Long rideId, Long driverId) { return null; }
            public ResponseDto completeRide(Long rideId, Long driverId) { return null; }
            public ResponseDto cancelRide(Long rideId, Long userId) { return null; }
            public ResponseDto cancelRideByDriver(Long rideId, Long driverId) { return null; }
            public double calculateFare(Location pickup, Location drop, VehicleType vehicleType) { return 0; }
        };

        ratingService.setRideService(testRideService);
    }


    @Test
    void testRateRideSuccess() throws Exception {
        Rating r = new Rating(null, 1L, 1L, 1L, 5, "Good");
        assertTrue(ratingService.rateRide(r).isSuccess());
    }

    @Test
    void testRateRideInvalidLow() {
        Rating r = new Rating(null, 1L, 1L, 1L, 0, "Too low");
        assertThrows(IllegalArgumentException.class, () -> ratingService.rateRide(r));
    }

    @Test
    void testRateRideInvalidHigh() {
        Rating r = new Rating(null, 1L, 1L, 1L, 6, "Too high");
        assertThrows(IllegalArgumentException.class, () -> ratingService.rateRide(r));
    }

    @Test
    void testGetRatingByRideId_NotFound() {
        assertThrows(Exception.class, () -> ratingService.getRatingByRideId(999L));
    }

    @Test
    void testGetRatingsByUserId() throws Exception {
        ratingService.rateRide(new Rating(null, 2L, 2L, 2L, 4, "Nice"));
        assertFalse(ratingService.getRatingsByUserId(2L).isEmpty());
    }

    @Test
    void testGetAverageDriverRating() throws Exception {
        ratingService.rateRide(new Rating(null, 3L, 3L, 5L, 4, ""));
        ratingService.rateRide(new Rating(null, 4L, 4L, 5L, 5, ""));
        assertEquals(4.5, ratingService.getAverageDriverRating(5L), 0.01);
    }

    @Test
    void testGetAllRatings() throws Exception {
        ratingService.rateRide(new Rating(null, 5L, 5L, 6L, 5, ""));
        assertTrue(ratingService.getAllRatings().size() > 0);
    }
}


