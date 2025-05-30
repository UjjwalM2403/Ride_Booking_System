package com.abes.dto;

import com.abes.enums.VehicleType;
import com.abes.enums.RideStatus;
import java.time.LocalDateTime;

public class RideRequest {
    private Long requestId;
    private Long userId;
    private Location pickupLocation;
    private Location dropLocation;
    private VehicleType preferredVehicleType;
    private LocalDateTime requestTime;
    private RideStatus status;
    
    public RideRequest() {}
    
    public RideRequest(Long requestId, Long userId, Location pickupLocation, Location dropLocation, VehicleType preferredVehicleType) {
        this.requestId = requestId;
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.preferredVehicleType = preferredVehicleType;
        this.requestTime = LocalDateTime.now();
        this.status = RideStatus.REQUESTED;
    }
    
    // Getters and Setters
    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Location getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(Location pickupLocation) { this.pickupLocation = pickupLocation; }
    public Location getDropLocation() { return dropLocation; }
    public void setDropLocation(Location dropLocation) { this.dropLocation = dropLocation; }
    public VehicleType getPreferredVehicleType() { return preferredVehicleType; }
    public void setPreferredVehicleType(VehicleType preferredVehicleType) { this.preferredVehicleType = preferredVehicleType; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
    
    @Override
    public String toString() {
        return "RideRequest{id=" + requestId + ", vehicleType=" + preferredVehicleType + ", status=" + status + "}";
    }
}