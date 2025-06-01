package com.abes.util;

public class Constants {
    // Application constants
    public static final String APP_NAME = "RideShare Application";
    public static final String VERSION = "1.0.0";
    
    // Validation constants
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 255;
    
    // Business logic constants
    public static final double PRICE_PER_KM = 10.0; // Price per kilometer
    public static final double MAX_SEARCH_RADIUS_KM = 20.0; // Maximum search radius for drivers
    public static final int RATING_MIN = 1;
    public static final int RATING_MAX = 5;
    
    // Time constants (in minutes)
    public static final int RIDE_REQUEST_TIMEOUT = 10;
    public static final int DRIVER_RESPONSE_TIMEOUT = 5;
    
    // Distance calculation constants
    public static final double EARTH_RADIUS_KM = 6371.0;
    
    // Payment constants
    public static final double MINIMUM_FARE = 25.0;
    public static final double BOOKING_FEE = 5.0;
    public static final double SERVICE_TAX_PERCENTAGE = 0.18; // 18% GST
}
