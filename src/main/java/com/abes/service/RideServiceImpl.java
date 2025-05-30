package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.enums.*;
import com.abes.util.Constants;
import java.util.List;
import java.time.LocalDateTime;

public class RideServiceImpl implements RideService {
    private RideDao rideDao = new RideDaoImpl();
    private UserService userService = new UserServiceImpl();
    private DriverService driverService = new DriverServiceImpl();
    
    @Override
    public ResponseDto requestRide(RideRequest rideRequest) throws UserNotFoundException, InvalidRideException {
        // Validate user exists
        userService.getUserById(rideRequest.getUserId());
        
        // Validate locations
        if (rideRequest.getPickupLocation() == null || rideRequest.getDropLocation() == null) {
            throw new InvalidRideException("Pickup and drop locations are required");
        }
        
        // Create ride from request
        Ride ride = new Ride();
        ride.setUserId(rideRequest.getUserId());
        ride.setPickupLocation(rideRequest.getPickupLocation());
        ride.setDropLocation(rideRequest.getDropLocation());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setRequestTime(LocalDateTime.now());
        
        // Calculate fare
        double fare = calculateFare(
            rideRequest.getPickupLocation(),
            rideRequest.getDropLocation(),
            rideRequest.getPreferredVehicleType()
        );
        ride.setFare(fare);
        
        Ride savedRide = rideDao.save(ride);
        return new ResponseDto(true, "Ride requested successfully", savedRide);
    }
    
    @Override
    public ResponseDto acceptRide(Long rideId, Long driverId) throws RideNotFoundException, DriverNotFoundException, InvalidRideException {
        Ride ride = getRideById(rideId);
        Driver driver = driverService.getDriverById(driverId);
        
        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new InvalidRideException("Ride is not available for acceptance");
        }
        
        if (!driver.getIsAvailable()) {
            throw new InvalidRideException("Driver is not available");
        }
        
        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);
        rideDao.update(ride);
        
        // Update driver availability
        driverService.updateDriverAvailability(driverId, false);
        
        return new ResponseDto(true, "Ride accepted successfully", ride);
    }
    
    @Override
    public ResponseDto startRide(Long rideId) throws RideNotFoundException, InvalidRideException {
        Ride ride = getRideById(rideId);
        
        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new InvalidRideException("Ride must be accepted before starting");
        }
        
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartTime(LocalDateTime.now());
        rideDao.update(ride);
        
        return new ResponseDto(true, "Ride started successfully", ride);
    }
    
    @Override
    public ResponseDto completeRide(Long rideId) throws RideNotFoundException, InvalidRideException {
        Ride ride = getRideById(rideId);
        
        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new InvalidRideException("Ride must be in progress to complete");
        }
        
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        rideDao.update(ride);
        
        // Make driver available again
        try {
            driverService.updateDriverAvailability(ride.getDriverId(), true);
        } catch (DriverNotFoundException e) {
            // Log error but don't fail the ride completion
        }
        
        return new ResponseDto(true, "Ride completed successfully", ride);
    }
    
    @Override
    public ResponseDto cancelRide(Long rideId) throws RideNotFoundException, InvalidRideException {
        Ride ride = getRideById(rideId);
        
        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new InvalidRideException("Cannot cancel a completed ride");
        }
        
        ride.setStatus(RideStatus.CANCELLED);
        rideDao.update(ride);
        
        // Make driver available again if assigned
        if (ride.getDriverId() != null) {
            try {
                driverService.updateDriverAvailability(ride.getDriverId(), true);
            } catch (DriverNotFoundException e) {
                // Log error but don't fail the cancellation
            }
        }
        
        return new ResponseDto(true, "Ride cancelled successfully", ride);
    }
    
    @Override
    public Ride getRideById(Long id) throws RideNotFoundException {
        Ride ride = rideDao.findById(id);
        if (ride == null) {
            throw new RideNotFoundException("Ride not found with ID: " + id);
        }
        return ride;
    }
    
    @Override
    public List<Ride> getRidesByUserId(Long userId) {
        return rideDao.findByUserId(userId);
    }
    
    @Override
    public List<Ride> getRidesByDriverId(Long driverId) {
        return rideDao.findByDriverId(driverId);
    }
    
    @Override
    public List<Ride> getAllRides() {
        return rideDao.findAll();
    }
    
    @Override
    public double calculateFare(Location pickup, Location drop, VehicleType vehicleType) {
        double distance = pickup.calculateDistance(drop);
        double baseFare = vehicleType != null ? vehicleType.getBaseFare() : 50.0;
        return baseFare + (distance * Constants.PRICE_PER_KM);
    }
}
