package com.abes.dto;

public class Location {
    private double latitude;
    private double longitude;
    private String address;

    public Location() {}

    // Constructor with only latitude and longitude
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = null; // or you can set a default address
    }

    // Constructor with latitude, longitude, and address
    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double calculateDistance(Location other) {
        // Simple distance calculation (in km)
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(other.latitude);
        double deltaLatRad = Math.toRadians(other.latitude - this.latitude);
        double deltaLonRad = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(deltaLatRad/2) * Math.sin(deltaLatRad/2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(deltaLonRad/2) * Math.sin(deltaLonRad/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return 6371 * c; // Earth's radius in km
    }

    // Getters and Setters
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        if (address != null && !address.isEmpty()) {
            return address + " (" + latitude + ", " + longitude + ")";
        } else {
            return "(" + latitude + ", " + longitude + ")";
        }
    }
}