package com.abes.service;

import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.enums.VehicleType;
import java.util.List;

public interface RideService {
    ResponseDto requestRide(RideRequest rideRequest) throws UserNotFoundException, InvalidRideException;
    ResponseDto acceptRide(Long rideId, Long driverId) throws RideNotFoundException, DriverNotFoundException, InvalidRideException;
    ResponseDto startRide(Long rideId) throws RideNotFoundException, InvalidRideException;
    ResponseDto completeRide(Long rideId) throws RideNotFoundException, InvalidRideException;
    ResponseDto cancelRide(Long rideId) throws RideNotFoundException, InvalidRideException;
    Ride getRideById(Long id) throws RideNotFoundException;
    List<Ride> getRidesByUserId(Long userId);
    List<Ride> getRidesByDriverId(Long driverId);
    List<Ride> getAllRides();
    double calculateFare(Location pickup, Location drop, VehicleType vehicleType);
}