package com.abes.dto;

import com.abes.enums.VehicleType;

public class Vehicle {
    private Long vehicleId;
    private String plateNumber;
    private VehicleType type;
    private String model;
    private String color;
    private Integer capacity;
    private Long driverId;
    
    public Vehicle() {}
    
    public Vehicle(Long vehicleId, String plateNumber, VehicleType type, String model, String color, Integer capacity, Long driverId) {
        this.vehicleId = vehicleId;
        this.plateNumber = plateNumber;
        this.type = type;
        this.model = model;
        this.color = color;
        this.capacity = capacity;
        this.driverId = driverId;
    }
    
    // Getters and Setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    
    @Override
    public String toString() {
        return type + " - " + model + " (" + plateNumber + ")";
    }
}
