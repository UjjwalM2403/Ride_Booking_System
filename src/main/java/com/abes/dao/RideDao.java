package com.abes.dao;

import com.abes.dto.Ride;
import java.util.List;

public interface RideDao {
    Ride save(Ride ride);
    Ride findById(Long id);
    List<Ride> findByUserId(Long userId);
    List<Ride> findByDriverId(Long driverId);
    List<Ride> findAll();
    Ride update(Ride ride);
    boolean delete(Long id);
}