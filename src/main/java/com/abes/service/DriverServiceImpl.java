package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.util.*;
import java.util.List;
import java.util.ArrayList;

public class DriverServiceImpl implements DriverService {
    private DriverDao driverDao = new DriverDaoImpl();
    private VehicleDao vehicleDao = new VehicleDaoImpl();
    
    @Override
    public ResponseDto registerDriver(Driver driver, Vehicle vehicle) throws InvalidCredentialsException {
        // Validate driver input
        if (!ValidationUtil.isValidEmail(driver.getEmail())) {
            throw new InvalidCredentialsException("Invalid email format");
        }
        if (!ValidationUtil.isValidPhone(driver.getPhone())) {
            throw new InvalidCredentialsException("Invalid phone number");
        }
        if (!ValidationUtil.isNotEmpty(driver.getName())) {
            throw new InvalidCredentialsException("Name cannot be empty");
        }
        if (!ValidationUtil.isNotEmpty(driver.getLicenseNumber())) {
            throw new InvalidCredentialsException("License number cannot be empty");
        }
        
        // Check if driver already exists
        if (driverDao.findByEmail(driver.getEmail()) != null) {
            throw new InvalidCredentialsException("Driver with this email already exists");
        }
        
        // Save driver
        Driver savedDriver = driverDao.save(driver);
        
        // Save vehicle if provided
        if (vehicle != null) {
            vehicle.setDriverId(savedDriver.getDriverId());
            Vehicle savedVehicle = vehicleDao.save(vehicle);
            savedDriver.setVehicle(savedVehicle);
        }
        
        return new ResponseDto(true, "Driver registered successfully", savedDriver);
    }
    
    @Override
    public Driver getDriverById(Long id) throws DriverNotFoundException {
        Driver driver = driverDao.findById(id);
        if (driver == null) {
            throw new DriverNotFoundException("Driver not found with ID: " + id);
        }
        
        // Load vehicle information
        Vehicle vehicle = vehicleDao.findByDriverId(id);
        driver.setVehicle(vehicle);
        
        return driver;
    }
    
    @Override
    public Driver getDriverByEmail(String email) throws DriverNotFoundException {
        Driver driver = driverDao.findByEmail(email);
        if (driver == null) {
            throw new DriverNotFoundException("Driver not found with email: " + email);
        }
        
        // Load vehicle information
        Vehicle vehicle = vehicleDao.findByDriverId(driver.getDriverId());
        driver.setVehicle(vehicle);
        
        return driver;
    }
    
    @Override
    public List<Driver> getAvailableDrivers() {
        return driverDao.findAvailableDrivers();
    }
    
    @Override
    public List<Driver> getAllDrivers() {
        return driverDao.findAll();
    }
    
    @Override
    public ResponseDto updateDriver(Driver driver) throws DriverNotFoundException {
        Driver existingDriver = getDriverById(driver.getDriverId());
        driverDao.update(driver);
        return new ResponseDto(true, "Driver updated successfully", driver);
    }
    
    @Override
    public ResponseDto deleteDriver(Long id) throws DriverNotFoundException {
        Driver driver = getDriverById(id);
        driverDao.delete(id);
        return new ResponseDto(true, "Driver deleted successfully");
    }
    
    @Override
    public ResponseDto updateDriverLocation(Long driverId, Location location) throws DriverNotFoundException {
        Driver driver = getDriverById(driverId);
        driver.setCurrentLocation(location);
        driverDao.update(driver);
        return new ResponseDto(true, "Driver location updated successfully");
    }
    
    @Override
    public ResponseDto updateDriverAvailability(Long driverId, Boolean isAvailable) throws DriverNotFoundException {
        Driver driver = getDriverById(driverId);
        driver.setIsAvailable(isAvailable);
        driverDao.update(driver);
        return new ResponseDto(true, "Driver availability updated successfully");
    }
    
    @Override
    public List<Driver> findNearbyDrivers(Location location, double radiusKm) {
        List<Driver> availableDrivers = getAvailableDrivers();
        List<Driver> nearbyDrivers = new ArrayList<>();
        
        for (Driver driver : availableDrivers) {
            if (driver.getCurrentLocation() != null) {
                double distance = location.calculateDistance(driver.getCurrentLocation());
                if (distance <= radiusKm) {
                    nearbyDrivers.add(driver);
                }
            }
        }
        
        return nearbyDrivers;
    }
}