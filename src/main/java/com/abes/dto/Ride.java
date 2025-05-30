package com.abes.dto;

import com.abes.enums.RideStatus;
import java.time.LocalDateTime;

public class Ride {
    private Long rideId;
    private Long userId;
    private Long driverId;
    private Location pickupLocation;
    private Location dropLocation;
    private RideStatus status;
    private Double fare;
    private LocalDateTime requestTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public Ride() {}
    
    public Ride(Long rideId, Long userId, Location pickupLocation, Location dropLocation) {
        this.rideId = rideId;
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.status = RideStatus.REQUESTED;
        this.requestTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public Location getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(Location pickupLocation) { this.pickupLocation = pickupLocation; }
    public Location getDropLocation() { return dropLocation; }
    public void setDropLocation(Location dropLocation) { this.dropLocation = dropLocation; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
    public Double getFare() { return fare; }
    public void setFare(Double fare) { this.fare = fare; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    @Override
    public String toString() {
        return "Ride{id=" + rideId + ", status=" + status + ", fare=" + fare + "}";
    }
}
