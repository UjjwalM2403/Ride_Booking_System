package com.abes.dao;

import com.abes.dto.Vehicle;
import java.util.List;

public interface VehicleDao {
    Vehicle save(Vehicle vehicle);
    Vehicle findById(Long id);
    Vehicle findByDriverId(Long driverId);
    List<Vehicle> findAll();
    Vehicle update(Vehicle vehicle);
    boolean delete(Long id);
}
