package com.abes.dto;

public class Rating {
    private Long ratingId;
    private Long rideId;
    private Long userId;
    private Long driverId;
    private Integer userRating;
    private Integer driverRating;
    private String feedback;
    
    public Rating() {}
    
    public Rating(Long ratingId, Long rideId, Long userId, Long driverId, Integer userRating, String feedback) {
        this.ratingId = ratingId;
        this.rideId = rideId;
        this.userId = userId;
        this.driverId = driverId;
        this.userRating = userRating;
        this.feedback = feedback;
    }
    
    // Getters and Setters
    public Long getRatingId() { return ratingId; }
    public void setRatingId(Long ratingId) { this.ratingId = ratingId; }
    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public Integer getUserRating() { return userRating; }
    public void setUserRating(Integer userRating) { this.userRating = userRating; }
    public Integer getDriverRating() { return driverRating; }
    public void setDriverRating(Integer driverRating) { this.driverRating = driverRating; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    @Override
    public String toString() {
        return "Rating{rideId=" + rideId + ", rating=" + userRating + ", feedback='" + feedback + "'}";
    }
}