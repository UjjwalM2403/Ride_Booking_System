package com.abes.service;

import com.abes.dto.*;
import com.abes.exception.*;
import com.abes.enums.UserRole;
import java.util.List;

public interface UserService {
    ResponseDto registerUser(User user) throws InvalidCredentialsException;
    ResponseDto loginUser(LoginDto loginDto) throws UserNotFoundException, InvalidCredentialsException;
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    List<User> getAllUsers();
    ResponseDto updateUser(User user) throws UserNotFoundException;
    ResponseDto deleteUser(Long id) throws UserNotFoundException;
    ResponseDto updateUserLocation(Long userId, Location location) throws UserNotFoundException;
}