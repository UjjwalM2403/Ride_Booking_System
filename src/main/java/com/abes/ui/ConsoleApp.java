package com.abes.ui;
//RideShareApp.java - Complete Main Application

import com.abes.dto.*;
import com.abes.service.*;
import com.abes.enums.*;
import com.abes.exception.*;     
import java.util.Scanner;
import java.util.List;

public class ConsoleApp {
 private static UserService userService = new UserServiceImpl();
 private static DriverService driverService = new DriverServiceImpl();
 private static RideService rideService = new RideServiceImpl();
 private static PaymentService paymentService = new PaymentServiceImpl();
 private static RatingService ratingService = new RatingServiceImpl();
 private static Scanner scanner = new Scanner(System.in);
 
 private static String repeatString(String str, int times) {
     StringBuilder sb = new StringBuilder();
     for (int i = 0; i < times; i++) {
         sb.append(str);
     }
     return sb.toString();
 }
 public static void main(String[] args) {
     System.out.println(repeatString("=",60));
     System.out.println("      WELCOME TO RIDE SHARE APPLICATION!");
     System.out.println(repeatString("=",60));
     
     // Initialize sample data
     initializeSampleData();
     
     while (true) {
         displayMainMenu();
         int choice = getIntInput();
         
         try {
             switch (choice) {
                 case 1:
                     registerUser();
                     break;
                 case 2:
                     registerDriver();
                     break;
                 case 3:
                     loginUser();
                     break;
                 case 4:
                     requestRide();
                     break;
                 case 5:
                     manageRides();
                     break;
                 case 6:
                     viewAllRides();
                     break;
                 case 7:
                     viewAllDrivers();
                     break;
                 case 8:
                     viewAllUsers();
                     break;
                 case 9:
                     processPayment();
                     break;
                 case 10:
                     rateRide();
                     break;
                 case 11:
                     viewDriverRatings();
                     break;
                 case 12:
                     findNearbyDrivers();
                     break;
                 case 0:
                     System.out.println("\n" + repeatString("=",60));
                     System.out.println("   Thank you for using Ride Share Application!");
                     System.out.println(repeatString("=",60));
                     return;
                 default:
                     System.out.println("❌ Invalid choice. Please try again.");
             }
         } catch (Exception e) {
             System.out.println("❌ Error: " + e.getMessage());
         }
         
         System.out.println("\nPress Enter to continue...");
         scanner.nextLine();
     }
 }
 
 private static void displayMainMenu() {
     System.out.println("\n" + repeatString("=",60));
     System.out.println("                    MAIN MENU");
     System.out.println(repeatString("=",60));
     System.out.println("👤 USER MANAGEMENT:");
     System.out.println("  1. Register User");
     System.out.println("  2. Register Driver");
     System.out.println("  3. Login User");
     System.out.println("\n🚗 RIDE SERVICES:");
     System.out.println("  4. Request Ride");
     System.out.println("  5. Manage Rides (Accept/Start/Complete/Cancel)");
     System.out.println("  6. View All Rides");
     System.out.println("\n📊 VIEW DATA:");
     System.out.println("  7. View All Drivers");
     System.out.println("  8. View All Users");
     System.out.println("\n💳 PAYMENT & RATING:");
     System.out.println("  9. Process Payment");
     System.out.println(" 10. Rate Ride");
     System.out.println(" 11. View Driver Ratings");
     System.out.println("\n🔍 UTILITIES:");
     System.out.println(" 12. Find Nearby Drivers");
     System.out.println("\n  0. Exit");
     System.out.println(repeatString("=",60));
     System.out.print("Enter your choice: ");
 }
 
 private static void registerUser() throws InvalidCredentialsException {
     System.out.println("\n" + repeatString("=",40));
     System.out.println("         USER REGISTRATION");
     System.out.println(repeatString("=",40));
     
     System.out.print("👤 Enter name: ");
     String name = scanner.nextLine();
     System.out.print("📧 Enter email: ");
     String email = scanner.nextLine();
     System.out.print("📱 Enter phone: ");
     String phone = scanner.nextLine();
     System.out.print("🔒 Enter password: ");
     String password = scanner.nextLine();
     
     User user = new User();
     user.setName(name);
     user.setEmail(email);
     user.setPhone(phone);
     user.setPassword(password);
     user.setRole(UserRole.USER);
     
     ResponseDto response = userService.registerUser(user);
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         User savedUser = (User) response.getData();
         System.out.println("🆔 User ID: " + savedUser.getUserId());
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void registerDriver() throws InvalidCredentialsException {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("        DRIVER REGISTRATION");
     System.out.println(repeatString("=",40));
     
     System.out.print("👤 Enter name: ");
     String name = scanner.nextLine();
     System.out.print("📧 Enter email: ");
     String email = scanner.nextLine();
     System.out.print("📱 Enter phone: ");
     String phone = scanner.nextLine();
     System.out.print("🪪 Enter license number: ");
     String licenseNumber = scanner.nextLine();
     
     Driver driver = new Driver();
     driver.setName(name);
     driver.setEmail(email);
     driver.setPhone(phone);
     driver.setLicenseNumber(licenseNumber);
     driver.setIsAvailable(true);
     
     // Set default location (Delhi)
     driver.setCurrentLocation(new Location(28.6139, 77.2090));
     
     System.out.println("\n--- Vehicle Information ---");
     System.out.print("🚙 Enter vehicle model: ");
     String model = scanner.nextLine();
     System.out.print("🔢 Enter license plate: ");
     String plateNumber = scanner.nextLine();
     System.out.print("🎨 Enter vehicle color: ");
     String color = scanner.nextLine();
     System.out.print("👥 Enter vehicle capacity: ");
     int capacity = getIntInput();
     
     System.out.println("\nSelect vehicle type:");
     System.out.println("1. SEDAN (Base: $50)");
     System.out.println("2. SUV (Base: $70)");
     System.out.println("3. HATCHBACK (Base: $40)");
     System.out.println("4. BIKE (Base: $100)");
     int typeChoice = getIntInput();
     
     VehicleType vehicleType = VehicleType.SEDAN;
     switch (typeChoice) {
         case 2: vehicleType = VehicleType.SUV; break;
         case 3: vehicleType = VehicleType.HATCHBACK; break;
         case 4: vehicleType = VehicleType.BIKE; break;
     }
     
     Vehicle vehicle = new Vehicle();
     vehicle.setModel(model);
     vehicle.setPlateNumber(plateNumber);
     vehicle.setColor(color);
     vehicle.setCapacity(capacity);
     vehicle.setType(vehicleType);
     
     ResponseDto response = driverService.registerDriver(driver, vehicle);
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         Driver savedDriver = (Driver) response.getData();
         System.out.println("🆔 Driver ID: " + savedDriver.getDriverId());
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void loginUser() throws UserNotFoundException, InvalidCredentialsException {
     System.out.println("\n" + repeatString("=",40));
     System.out.println("           USER LOGIN");
     System.out.println(repeatString("=",40));
     
     System.out.print("📧 Enter email: ");
     String email = scanner.nextLine();
     System.out.print("🔒 Enter password: ");
     String password = scanner.nextLine();
     
     LoginDto loginDto = new LoginDto();
     loginDto.setEmail(email);
     loginDto.setPassword(password);
     
     ResponseDto response = userService.loginUser(loginDto);
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         User user = (User) response.getData();
         System.out.println("🎉 Welcome, " + user.getName() + "!");
         System.out.println("🆔 Your User ID: " + user.getUserId());
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void requestRide() throws UserNotFoundException, InvalidRideException {
     System.out.println("\n" + repeatString("=",40));
     System.out.println("          REQUEST RIDE");
     System.out.println(repeatString("=",40));
     
     System.out.print("🆔 Enter user ID: ");
     Long userId = getLongInput();
     
     System.out.println("\n📍 Enter pickup location:");
     System.out.print("   Latitude: ");
     double pickupLat = getDoubleInput();
     System.out.print("   Longitude: ");
     double pickupLng = getDoubleInput();
     
     System.out.println("\n🎯 Enter drop location:");
     System.out.print("   Latitude: ");
     double dropLat = getDoubleInput();
     System.out.print("   Longitude: ");
     double dropLng = getDoubleInput();
     
     Location pickupLocation = new Location(pickupLat, pickupLng);
     Location dropLocation = new Location(dropLat, dropLng);
     
     System.out.println("\nSelect preferred vehicle type:");
     System.out.println("1. SEDAN (Base: $50)");
     System.out.println("2. SUV (Base: $70)");
     System.out.println("3. HATCHBACK (Base: $40)");
     System.out.println("4. BIKE (Base: $100)");
     int typeChoice = getIntInput();
     
     VehicleType vehicleType = VehicleType.SEDAN;
     switch (typeChoice) {
         case 2: vehicleType = VehicleType.SUV; break;
         case 3: vehicleType = VehicleType.HATCHBACK; break;
         case 4: vehicleType = VehicleType.BIKE; break;
     }
     
     RideRequest rideRequest = new RideRequest();
     rideRequest.setUserId(userId);
     rideRequest.setPickupLocation(pickupLocation);
     rideRequest.setDropLocation(dropLocation);
     rideRequest.setPreferredVehicleType(vehicleType);
     
     ResponseDto response = rideService.requestRide(rideRequest);
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         Ride ride = (Ride) response.getData();
         System.out.println("🆔 Ride ID: " + ride.getRideId());
         System.out.println("💰 Estimated Fare: $" + String.format("%.2f", ride.getFare()));
         
         // Show nearby drivers
         List<Driver> nearbyDrivers = driverService.findNearbyDrivers(pickupLocation, 10.0);
         if (!nearbyDrivers.isEmpty()) {
             System.out.println("\n🚗 Nearby available drivers:");
             System.out.println(repeatString("-",60));
             for (Driver driver : nearbyDrivers) {
                 double distance = pickupLocation.calculateDistance(driver.getCurrentLocation());
                 System.out.printf("🆔 ID: %-3d | 👤 %-15s | 📱 %-12s | 📍 %.2f km%n", 
                                 driver.getDriverId(), 
                                 driver.getName(), 
                                 driver.getPhone(),
                                 distance);
             }
             System.out.println(repeatString("-",60));
             
             // Auto-assign closest driver option
             System.out.print("\nWould you like to auto-assign the closest driver? (y/n): ");
             String autoAssign = scanner.nextLine();
             
             if (autoAssign.toLowerCase().startsWith("y")) {
                 // Find closest driver
                 Driver closestDriver = nearbyDrivers.get(0);
                 double minDistance = pickupLocation.calculateDistance(closestDriver.getCurrentLocation());
                 
                 for (Driver driver : nearbyDrivers) {
                     double distance = pickupLocation.calculateDistance(driver.getCurrentLocation());
                     if (distance < minDistance) {
                         minDistance = distance;
                         closestDriver = driver;
                     }
                 }
                 
                 try {
                     ResponseDto acceptResponse = rideService.acceptRide(ride.getRideId(), closestDriver.getDriverId());
                     System.out.println("✅ " + acceptResponse.getMessage());
                     System.out.println("👤 Driver: " + closestDriver.getName() + " (" + closestDriver.getPhone() + ")");
                     
                     if (acceptResponse.isSuccess()) {
                         // Start ride automatically for demo
                         ResponseDto startResponse = rideService.startRide(ride.getRideId());
                         System.out.println("✅ " + startResponse.getMessage());
                     }
                 } catch (Exception e) {
                     System.out.println("❌ Error assigning driver: " + e.getMessage());
                 }
             }
         } else {
             System.out.println("❌ No nearby drivers available at the moment.");
         }
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void manageRides() {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("          MANAGE RIDES");
     System.out.println(repeatString("=",40));
     
     System.out.println("1. Accept Ride");
     System.out.println("2. Start Ride");
     System.out.println("3. Complete Ride");
     System.out.println("4. Cancel Ride");
     System.out.print("Enter choice: ");
     
     int choice = getIntInput();
     System.out.print("🆔 Enter ride ID: ");
     Long rideId = getLongInput();
     
     try {
         ResponseDto response = null;
         switch (choice) {
             case 1:
                 System.out.print("🆔 Enter driver ID: ");
                 Long driverId = getLongInput();
                 response = rideService.acceptRide(rideId, driverId);
                 break;
             case 2:
                 response = rideService.startRide(rideId);
                 break;
             case 3:
                 response = rideService.completeRide(rideId);
                 break;
             case 4:
                 response = rideService.cancelRide(rideId);
                 break;
             default:
                 System.out.println("❌ Invalid choice.");
                 return;
         }
         
         if (response.isSuccess()) {
             System.out.println("✅ " + response.getMessage());
         } else {
             System.out.println("❌ " + response.getMessage());
         }
     } catch (Exception e) {
         System.out.println("❌ Error: " + e.getMessage());
     }
 }
 
 private static void viewAllRides() {
     System.out.println("\n" +repeatString("=",80));
     System.out.println("                             ALL RIDES");
     System.out.println(repeatString("=",80));
     
     List<Ride> rides = rideService.getAllRides();
     
     if (rides.isEmpty()) {
         System.out.println("❌ No rides found.");
         return;
     }
     
     System.out.printf("%-5s %-8s %-10s %-15s %-10s %-20s%n", 
                      "ID", "User", "Driver", "Status", "Fare", "Request Time");
     System.out.println(repeatString("=",80));
     
     for (Ride ride : rides) {
         System.out.printf("%-5d %-8d %-10s %-15s $%-9.2f %-20s%n",
                          ride.getRideId(),
                          ride.getUserId(),
                          ride.getDriverId() != null ? ride.getDriverId().toString() : "N/A",
                          ride.getStatus(),
                          ride.getFare(),
                          ride.getRequestTime() != null ? 
                              ride.getRequestTime().toString().substring(0, 16) : "N/A");
     }
     System.out.println(repeatString("=",80));
     System.out.println("Total Rides: " + rides.size());
 }
 
 private static void viewAllDrivers() {
     System.out.println("\n" +repeatString("=",90));
     System.out.println("                                ALL DRIVERS");
     System.out.println(repeatString("=",90));
     
     List<Driver> drivers = driverService.getAllDrivers();
     
     if (drivers.isEmpty()) {
         System.out.println("❌ No drivers found.");
         return;
     }
     
     System.out.printf("%-5s %-20s %-25s %-15s %-12s %-10s%n", 
                      "ID", "Name", "Email", "Phone", "License", "Available");
     System.out.println(repeatString("-",90));
     
     for (Driver driver : drivers) {
         System.out.printf("%-5d %-20s %-25s %-15s %-12s %-10s%n",
                          driver.getDriverId(),
                          driver.getName(),
                          driver.getEmail(),
                          driver.getPhone(),
                          driver.getLicenseNumber(),
                          driver.getIsAvailable() ? "✅ Yes" : "❌ No");
     }
     System.out.println(repeatString("-",90));
     System.out.println("Total Drivers: " + drivers.size());
 }
 
 private static void viewAllUsers() {
     System.out.println("\n" + repeatString("=",80));
     System.out.println("                              ALL USERS");
     System.out.println(repeatString("=",80));
     
     List<User> users = userService.getAllUsers();
     
     if (users.isEmpty()) {
         System.out.println("❌ No users found.");
         return;
     }
     
     System.out.printf("%-5s %-20s %-30s %-15s %-10s%n", 
                      "ID", "Name", "Email", "Phone", "Role");
     System.out.println(repeatString("-",80));
     
     for (User user : users) {
         System.out.printf("%-5d %-20s %-30s %-15s %-10s%n",
                          user.getUserId(),
                          user.getName(),
                          user.getEmail(),
                          user.getPhone(),
                          user.getRole());
     }
     System.out.println(repeatString("-",80));
     System.out.println("Total Users: " + users.size());
 }
 
 private static void processPayment() throws RideNotFoundException, PaymentException {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("        PROCESS PAYMENT");
     System.out.println(repeatString("=",40));
     
     System.out.print("🆔 Enter ride ID: ");
     Long rideId = getLongInput();
     
     System.out.println("\nSelect payment method:");
     System.out.println("1. 💳 CARD");
 
     System.out.println("2. 📱 UPI");
     System.out.println("3. 💵 CASH");
     System.out.println("4. 👛 WALLET");
     int methodChoice = getIntInput();
     
     PaymentMethod method = PaymentMethod.CASH;
     switch (methodChoice) {
         case 1: method = PaymentMethod.CARD; break;
         case 2: method = PaymentMethod.UPI; break;
         case 3: method = PaymentMethod.CASH; break;
         case 4: method = PaymentMethod.WALLET; break;
     }
     
     System.out.println("\n⏳ Processing payment...");
     ResponseDto response = paymentService.processPayment(rideId, method);
     
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         Payment payment = (Payment) response.getData();
         System.out.println("🆔 Payment ID: " + payment.getPaymentId());
         System.out.println("💰 Amount: $" + String.format("%.2f", payment.getAmount()));
         System.out.println("📊 Status: " + payment.getStatus());
         System.out.println("💳 Method: " + payment.getMethod());
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void rateRide() throws RideNotFoundException, UserNotFoundException, DriverNotFoundException {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("           RATE RIDE");
     System.out.println(repeatString("=",40));
     
     System.out.print("🆔 Enter ride ID: ");
     Long rideId = getLongInput();
     System.out.print("👤 Enter user ID: ");
     Long userId = getLongInput();
     System.out.print("🚗 Enter driver ID: ");
     Long driverId = getLongInput();
     System.out.print("⭐ Enter rating (1-5): ");
     int rating = getIntInput();
     System.out.print("💬 Enter feedback (optional): ");
     String feedback = scanner.nextLine();
     
     Rating rideRating = new Rating();
     rideRating.setRideId(rideId);
     rideRating.setUserId(userId);
     rideRating.setDriverId(driverId);
     rideRating.setUserRating(rating);
     rideRating.setFeedback(feedback.isEmpty() ? null : feedback);
     
     ResponseDto response = ratingService.rateRide(rideRating);
     if (response.isSuccess()) {
         System.out.println("✅ " + response.getMessage());
         double avgRating = ratingService.getAverageDriverRating(driverId);
         System.out.println("⭐ Driver's average rating: " + String.format("%.2f", avgRating) + "/5.0");
     } else {
         System.out.println("❌ " + response.getMessage());
     }
 }
 
 private static void viewDriverRatings() {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("        DRIVER RATINGS");
     System.out.println(repeatString("=",40));
     
     System.out.print("🆔 Enter driver ID: ");
     Long driverId = getLongInput();
     
     try {
         Driver driver = driverService.getDriverById(driverId);
         List<Rating> ratings = ratingService.getRatingsByDriverId(driverId);
         double avgRating = ratingService.getAverageDriverRating(driverId);
         
         System.out.println("\n👤 Driver: " + driver.getName());
         System.out.println("⭐ Average Rating: " + String.format("%.2f", avgRating) + "/5.0");
         System.out.println("📊 Total Ratings: " + ratings.size());
         
         if (!ratings.isEmpty()) {
             System.out.println("\n--- Recent Ratings ---");
             System.out.printf("%-8s %-8s %-8s %-30s%n", "Ride ID", "User ID", "Rating", "Feedback");
             System.out.println(repeatString("-",60));
             
             for (Rating rating : ratings) {
                 String feedback = rating.getFeedback() != null ? rating.getFeedback() : "No feedback";
                 if (feedback.length() > 25) {
                     feedback = feedback.substring(0, 25) + "...";
                 }
                 System.out.printf("%-8d %-8d %-8d %-30s%n",
                                  rating.getRideId(),
                                  rating.getUserId(),
                                  rating.getUserRating(),
                                  feedback);
             }
         }
     } catch (Exception e) {
         System.out.println("❌ Error: " + e.getMessage());
     }
 }
 
 private static void findNearbyDrivers() {
     System.out.println("\n" +repeatString("=",40));
     System.out.println("       FIND NEARBY DRIVERS");
     System.out.println(repeatString("=",40));
     
     System.out.println("📍 Enter location:");
     System.out.print("   Latitude: ");
     double lat = getDoubleInput();
     System.out.print("   Longitude: ");
     double lng = getDoubleInput();
     System.out.print("🔍 Enter search radius (km): ");
     double radius = getDoubleInput();
     
     Location location = new Location(lat, lng);
     List<Driver> nearbyDrivers = driverService.findNearbyDrivers(location, radius);
     
     if (nearbyDrivers.isEmpty()) {
         System.out.println("❌ No drivers found within " + radius + " km radius.");
         return;
     }
     
     System.out.println("\n🚗 Found " + nearbyDrivers.size() + " nearby drivers:");
     System.out.println(repeatString("-",70));
     System.out.printf("%-5s %-20s %-15s %-12s %-10s%n", "ID", "Name", "Phone", "Distance", "Vehicle");
     System.out.println(repeatString("-",70));
     
     for (Driver driver : nearbyDrivers) {
         double distance = location.calculateDistance(driver.getCurrentLocation());
         String vehicleInfo = "N/A";
         if (driver.getVehicle() != null) {
             vehicleInfo = driver.getVehicle().getType().toString();
         }
         
         System.out.printf("%-5d %-20s %-15s %-12s %-10s%n",
                          driver.getDriverId(),
                          driver.getName(),
                          driver.getPhone(),
                          String.format("%.2f km", distance),
                          vehicleInfo);
     }
     System.out.println(repeatString("-",70));
 }
 
 private static void initializeSampleData() {
     System.out.println("🔄 Initializing sample data...");
     
     try {
         // Create sample users
         User user1 = new User();
         user1.setName("John Doe");
         user1.setEmail("john@example.com");
         user1.setPhone("1234567890");
         user1.setPassword("password123");
         user1.setRole(UserRole.USER);
         user1.setCurrentLocation(new Location(28.6139, 77.2090)); // Delhi
         userService.registerUser(user1);
         
         User user2 = new User();
         user2.setName("Jane Smith");
         user2.setEmail("jane@example.com");
         user2.setPhone("9876543210");
         user2.setPassword("password456");
         user2.setRole(UserRole.USER);
         user2.setCurrentLocation(new Location(28.7041, 77.1025)); // Delhi
         userService.registerUser(user2);
         
         User user3 = new User();
         user3.setName("Alice Johnson");
         user3.setEmail("alice@example.com");
         user3.setPhone("5551234567");
         user3.setPassword("alice123");
         user3.setRole(UserRole.USER);
         user3.setCurrentLocation(new Location(28.6500, 77.2300)); // Delhi
         userService.registerUser(user3);
         
         // Create sample drivers with vehicles
         Driver driver1 = new Driver();
         driver1.setName("Mike Wilson");
         driver1.setEmail("mike@example.com");
         driver1.setPhone("5555551234");
         driver1.setLicenseNumber("DL123456789");
         driver1.setIsAvailable(true);
         driver1.setCurrentLocation(new Location(28.6280, 77.2060)); // Near Delhi
         
         Vehicle vehicle1 = new Vehicle();
         vehicle1.setModel("Camry");
         vehicle1.setPlateNumber("DL01AB1234");
         vehicle1.setColor("Silver");
         vehicle1.setCapacity(4);
         vehicle1.setType(VehicleType.SEDAN);
         
         driverService.registerDriver(driver1, vehicle1);

         
         Driver driver2 = new Driver();
         driver2.setName("Sarah Johnson");
         driver2.setEmail("sarah@example.com");
         driver2.setPhone("5555555678");
         driver2.setLicenseNumber("DL987654321");
         driver2.setIsAvailable(true);
         driver2.setCurrentLocation(new Location(28.6500, 77.2300)); // Near Delhi
         
         Vehicle vehicle2 = new Vehicle();
         vehicle2.setModel("Honda");
         vehicle2.setPlateNumber("DL02CD5678");
         vehicle2.setColor("White");
         vehicle2.setCapacity(4);
         vehicle2.setType(VehicleType.SEDAN);
         
         

         
         
         driverService.registerDriver(driver2, vehicle2);
         
         Driver driver3 = new Driver();
         driver3.setName("David Brown");
         driver3.setEmail("david@example.com");
         driver3.setPhone("555559999");
         driver3.setLicenseNumber("DL555666777");
         driver3.setIsAvailable(true);
         driver3.setCurrentLocation(new Location(28.6800, 77.1700)); // Near Delhi
         
         Vehicle vehicle3 = new Vehicle();
         vehicle3.setModel("BMW");
         vehicle3.setPlateNumber("DL03EF9999");
         vehicle3.setCapacity(6);
         vehicle3.setColor("Blue");
         vehicle3.setType(VehicleType.BIKE);
         
         
         
         driverService.registerDriver(driver3, vehicle3);
         
         Driver driver4 = new Driver();
         driver4.setName("Emma Davis");
         driver4.setEmail("emma@example.com");
         driver4.setPhone("5555557777");
         driver4.setLicenseNumber("DL111222333");
         driver4.setIsAvailable(false); // Not available
         driver4.setCurrentLocation(new Location(28.7200, 77.1000)); // Near Delhi
         
         Vehicle vehicle4 = new Vehicle();
    
         vehicle4.setModel("XUV500");
         vehicle3.setCapacity(8);
         vehicle3.setColor("Red");
         vehicle4.setPlateNumber("DL04GH3333");
         vehicle4.setType(VehicleType.SUV);
         
         driverService.registerDriver(driver4, vehicle4);
         
         System.out.println("✅ Sample data initialized successfully!");
         System.out.println("📊 Created 3 users and 4 drivers with vehicles");
         
     } catch (Exception e) {
         System.out.println("❌ Error initializing sample data: " + e.getMessage());
     }
 }
 
 // Utility methods for input handling
 private static int getIntInput() {
     while (true) {
         try {
             String input = scanner.nextLine().trim();
             return Integer.parseInt(input);
         } catch (NumberFormatException e) {
             System.out.print("❌ Invalid number. Please enter a valid integer: ");
         }
     }
 }
 
 private static long getLongInput() {
     while (true) {
         try {
             String input = scanner.nextLine().trim();
             return Long.parseLong(input);
         } catch (NumberFormatException e) {
             System.out.print("❌ Invalid number. Please enter a valid number: ");
         }
     }
 }
 
 private static double getDoubleInput() {
     while (true) {
         try {
             String input = scanner.nextLine().trim();
             return Double.parseDouble(input);
         } catch (NumberFormatException e) {
             System.out.print("❌ Invalid number. Please enter a valid decimal number: ");
         }
     }
 }
}
