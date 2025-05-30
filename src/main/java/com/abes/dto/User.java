package com.abes.dto;

import com.abes.enums.UserRole;

public class User {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private Location currentLocation;
    
    public User() {}
    
    public User(Long userId, String name, String email, String phone, String password, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location currentLocation) { this.currentLocation = currentLocation; }
    
    @Override
    public String toString() {
        return "User{id=" + userId + ", name='" + name + "', email='" + email + "', role=" + role + "}";
    }
}