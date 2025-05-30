package com.abes.dto;

public class Driver {
    private Long driverId;
    private String name;
    private String email;
    private String phone;
    private String licenseNumber;
    private Boolean isAvailable;
    private Location currentLocation;
    private Vehicle vehicle;
    
    public Driver() {}
    
    public Driver(Long driverId, String name, String email, String phone, String licenseNumber) {
        this.driverId = driverId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.isAvailable = true;
    }
    
    // Getters and Setters
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location currentLocation) { this.currentLocation = currentLocation; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    
    @Override
    public String toString() {
        return "Driver{id=" + driverId + ", name='" + name + "', license='" + licenseNumber + "', available=" + isAvailable + "}";
    }
}
