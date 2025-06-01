package com.abes.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Changed from Driver to Admin
import com.abes.dto.*;

public class AdminDaoImpl implements AdminDao {
	 private static Map<Long, Driver> drivers = new HashMap<>();
    private static Map<Long, Admin> admins = new HashMap<>(); // Changed from Driver to Admin, and drivers to admins
    private static Long nextId = 1L;

    @Override
    public Admin save(Admin admin) { 
        admin.setAdminId(nextId++); 
        admins.put(admin.getAdminId(), admin); 
        return admin;
    }

    @Override
    public Admin findById(Long id) { // Changed from Driver to Admin
        return admins.get(id); // Changed from drivers.get to admins.get
    }

    @Override
    public Admin findByEmail(String email) { // Changed from Driver to Admin
        return admins.values().stream() // Changed from drivers.values to admins.values
                .filter(admin -> admin.getEmail().equals(email)) // Changed from driver -> driver.getEmail to admin -> admin.getEmail
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
    public List<Admin> findAll() { // Changed from List<Driver> to List<Admin>
        return new ArrayList<>(admins.values()); // Changed from drivers.values to admins.values
    }

    @Override
    public Admin update(Admin admin) { // Changed from Driver to Admin
        if (admins.containsKey(admin.getAdminId())) { // Changed from drivers.containsKey to admins.containsKey, and driver.getDriverId to admin.getAdminId
            admins.put(admin.getAdminId(), admin); // Changed from drivers.put to admins.put, and driver.getDriverId to admin.getAdminId
            return admin;
        }
        return null;
    }

    
    
    @Override
    public boolean delete(Long id) {
        return admins.remove(id) != null; // Changed from drivers.remove to admins.remove
    }

	
}