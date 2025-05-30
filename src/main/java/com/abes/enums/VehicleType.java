package com.abes.enums;

public enum VehicleType {
    SEDAN(50.0), SUV(70.0), HATCHBACK(40.0), BIKE(30.0);
    
    private final double baseFare;
    
    VehicleType(double baseFare) {
        this.baseFare = baseFare;
    }
    
    public double getBaseFare() {
        return baseFare;
    }
}