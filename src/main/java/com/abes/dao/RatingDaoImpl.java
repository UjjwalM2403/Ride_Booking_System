package com.abes.dao;

import com.abes.dto.Rating;
import java.util.*;

public class RatingDaoImpl implements RatingDao {
    private static Map<Long, Rating> ratings = new HashMap<>();
    private static Long nextId = 1L;
    
    @Override
    public Rating save(Rating rating) {
        rating.setRatingId(nextId++);
        ratings.put(rating.getRatingId(), rating);
        return rating;
    }
    
    @Override
    public Rating findById(Long id) {
        return ratings.get(id);
    }
    
    @Override
    public Rating findByRideId(Long rideId) {
        return ratings.values().stream()
                .filter(rating -> rating.getRideId().equals(rideId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Rating> findByUserId(Long userId) {
        List<Rating> userRatings = new ArrayList<>();
        for (Rating rating : ratings.values()) {
            if (rating.getUserId().equals(userId)) {
                userRatings.add(rating);
            }
        }
        return userRatings;
    }
    
    @Override
    public List<Rating> findByDriverId(Long driverId) {
        List<Rating> driverRatings = new ArrayList<>();
        for (Rating rating : ratings.values()) {
            if (rating.getDriverId().equals(driverId)) {
                driverRatings.add(rating);
            }
        }
        return driverRatings;
    }
    
    @Override
    public List<Rating> findAll() {
        return new ArrayList<>(ratings.values());
    }
    
    @Override
    public Rating update(Rating rating) {
        if (ratings.containsKey(rating.getRatingId())) {
            ratings.put(rating.getRatingId(), rating);
            return rating;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return ratings.remove(id) != null;
    }
}
