package com.abes.service;

import com.abes.dto.*;
import com.abes.exception.*;
import java.util.List;

public interface DriverService {
    ResponseDto registerDriver(Driver driver, Vehicle vehicle) throws InvalidCredentialsException;
    Driver getDriverById(Long id) throws DriverNotFoundException;
    Driver getDriverByEmail(String email) throws DriverNotFoundException;
    
    List<Driver> getAvailableDrivers();
    List<Driver> getAllDrivers();
    
    ResponseDto updateDriver(Driver driver) throws DriverNotFoundException;
    ResponseDto deleteDriver(Long id) throws DriverNotFoundException;
    ResponseDto updateDriverLocation(Long driverId, Location location) throws DriverNotFoundException;
    ResponseDto updateDriverAvailability(Long driverId, Boolean isAvailable) throws DriverNotFoundException;
    List<Driver> findNearbyDrivers(Location location, double radiusKm);
}
