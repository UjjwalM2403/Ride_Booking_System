package com.abes.service;

import com.abes.dto.*;
import com.abes.exception.*;
import java.util.List;

public interface RatingService {
    ResponseDto rateRide(Rating rating) throws RideNotFoundException, UserNotFoundException, DriverNotFoundException;
    Rating getRatingById(Long id) throws Exception;
    Rating getRatingByRideId(Long rideId) throws Exception;
    List<Rating> getRatingsByUserId(Long userId);
    List<Rating> getRatingsByDriverId(Long driverId);
    double getAverageDriverRating(Long driverId);
    List<Rating> getAllRatings();
}
