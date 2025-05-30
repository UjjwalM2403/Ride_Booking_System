package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.util.ValidationUtil;
import java.util.List;

public class RatingServiceImpl implements RatingService {
    private RatingDao ratingDao = new RatingDaoImpl();
    private RideService rideService = new RideServiceImpl();
    private UserService userService = new UserServiceImpl();
    private DriverService driverService = new DriverServiceImpl();
    
    @Override
    public ResponseDto rateRide(Rating rating) throws RideNotFoundException, UserNotFoundException, DriverNotFoundException {
        // Validate ride exists and is completed
        Ride ride = rideService.getRideById(rating.getRideId());
        if (ride.getStatus() != com.abes.enums.RideStatus.COMPLETED) {
            throw new RideNotFoundException("Can only rate completed rides");
        }
        
        // Validate user and driver exist
        userService.getUserById(rating.getUserId());
        driverService.getDriverById(rating.getDriverId());
        
        // Validate rating value
        if (!ValidationUtil.isValidRating(rating.getUserRating())) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        // Check if rating already exists
        Rating existingRating = ratingDao.findByRideId(rating.getRideId());
        if (existingRating != null) {
            // Update existing rating
            existingRating.setUserRating(rating.getUserRating());
            existingRating.setFeedback(rating.getFeedback());
            ratingDao.update(existingRating);
            return new ResponseDto(true, "Rating updated successfully", existingRating);
        }
        
        Rating savedRating = ratingDao.save(rating);
        return new ResponseDto(true, "Rating submitted successfully", savedRating);
    }
    
    @Override
    public Rating getRatingById(Long id) throws Exception {
        Rating rating = ratingDao.findById(id);
        if (rating == null) {
            throw new Exception("Rating not found with ID: " + id);
        }
        return rating;
    }
    
    @Override
    public Rating getRatingByRideId(Long rideId) throws Exception {
        Rating rating = ratingDao.findByRideId(rideId);
        if (rating == null) {
            throw new Exception("Rating not found for ride ID: " + rideId);
        }
        return rating;
    }
    
    @Override
    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingDao.findByUserId(userId);
    }
    
    @Override
    public List<Rating> getRatingsByDriverId(Long driverId) {
        return ratingDao.findByDriverId(driverId);
    }
    
    @Override
    public double getAverageDriverRating(Long driverId) {
        List<Rating> ratings = getRatingsByDriverId(driverId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Rating rating : ratings) {
            total += rating.getUserRating();
        }
        
        return total / ratings.size();
    }
    
    @Override
    public List<Rating> getAllRatings() {
        return ratingDao.findAll();
    }
}