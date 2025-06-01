package com.abes.dao;

import com.abes.dto.Driver;
import java.util.*;

public class DriverDaoImpl implements DriverDao {
    private static Map<Long, Driver> drivers = new HashMap<>();
    private static Long nextId = 1L;
    
    @Override
    public Driver save(Driver driver) {
        driver.setDriverId(nextId++);
        drivers.put(driver.getDriverId(), driver);
        return driver;
    }
    
    @Override
    public Driver findById(Long id) {
        return drivers.get(id);
    }
    
    @Override
    public Driver findByEmail(String email) {
        return drivers.values().stream()
                .filter(driver -> driver.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Driver> findAvailableDrivers() {
        List<Driver> available = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.getIsAvailable()) {
                available.add(driver);
            }
        }
        return available;
    }
    
    @Override
    public List<Driver> findAll() {
        return new ArrayList<>(drivers.values());
    }
    
    @Override
    public Driver update(Driver driver) {
        if (drivers.containsKey(driver.getDriverId())) {
            drivers.put(driver.getDriverId(), driver);
            return driver;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return drivers.remove(id) != null;
    }
}
