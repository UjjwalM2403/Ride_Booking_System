package com.abes.dao;

import com.abes.dto.Rating;
import java.util.List;

public interface RatingDao {
    Rating save(Rating rating);
    Rating findById(Long id);
    Rating findByRideId(Long rideId);
    List<Rating> findByUserId(Long userId);
    List<Rating> findByDriverId(Long driverId);
    List<Rating> findAll();
    Rating update(Rating rating);
    boolean delete(Long id);
}