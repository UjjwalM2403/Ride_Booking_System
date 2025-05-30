package com.abes.service;

import com.abes.dao.*;
import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.util.*;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    
    @Override
    public ResponseDto registerUser(User user) throws InvalidCredentialsException {
        // Validate input
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new InvalidCredentialsException("Invalid email format");
        }
        if (!ValidationUtil.isValidPhone(user.getPhone())) {
            throw new InvalidCredentialsException("Invalid phone number");
        }
        if (!ValidationUtil.isValidPassword(user.getPassword())) {
            throw new InvalidCredentialsException("Password must be at least " + Constants.MIN_PASSWORD_LENGTH + " characters");
        }
        if (!ValidationUtil.isNotEmpty(user.getName())) {
            throw new InvalidCredentialsException("Name cannot be empty");
        }
        
        // Check if user already exists
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new InvalidCredentialsException("User with this email already exists");
        }
        
        // Hash password and save user
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        User savedUser = userDao.save(user);
        
        return new ResponseDto(true, "User registered successfully", savedUser);
    }
    
    @Override
    public ResponseDto loginUser(LoginDto loginDto) throws UserNotFoundException, InvalidCredentialsException {
        User user = userDao.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + loginDto.getEmail());
        }
        
        if (!PasswordUtil.verifyPassword(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        
        return new ResponseDto(true, "Login successful", user);
    }
    
    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        User user = userDao.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        return user;
    }
    
    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @Override
    public ResponseDto updateUser(User user) throws UserNotFoundException {
        User existingUser = getUserById(user.getUserId());
        userDao.update(user);
        return new ResponseDto(true, "User updated successfully", user);
    }
    
    @Override
    public ResponseDto deleteUser(Long id) throws UserNotFoundException {
        User user = getUserById(id);
        userDao.delete(id);
        return new ResponseDto(true, "User deleted successfully");
    }
    
    @Override
    public ResponseDto updateUserLocation(Long userId, Location location) throws UserNotFoundException {
        User user = getUserById(userId);
        user.setCurrentLocation(location);
        userDao.update(user);
        return new ResponseDto(true, "User location updated successfully");
    }
}