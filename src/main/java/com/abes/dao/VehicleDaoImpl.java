package com.abes.dao;

import com.abes.dto.Vehicle;
import java.util.*;

public class VehicleDaoImpl implements VehicleDao {
    private static Map<Long, Vehicle> vehicles = new HashMap<>();
    private static Long nextId = 1L;
    
    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicle.setVehicleId(nextId++);
        vehicles.put(vehicle.getVehicleId(), vehicle);
        return vehicle;
    }
    
    @Override
    public Vehicle findById(Long id) {
        return vehicles.get(id);
    }
    
    @Override
    public Vehicle findByDriverId(Long driverId) {
        return vehicles.values().stream()
                .filter(vehicle -> vehicle.getDriverId().equals(driverId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles.values());
    }
    
    @Override
    public Vehicle update(Vehicle vehicle) {
        if (vehicles.containsKey(vehicle.getVehicleId())) {
            vehicles.put(vehicle.getVehicleId(), vehicle);
            return vehicle;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return vehicles.remove(id) != null;
    }
}