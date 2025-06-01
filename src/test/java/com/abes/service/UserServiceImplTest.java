package com.abes.service;

import com.abes.dto.User;
import com.abes.dto.ResponseDto;
import com.abes.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    void testRegisterUserSuccess() throws InvalidCredentialsException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("9876543210");
        user.setPassword("strongPassword");
        user.setName("Test User");

        ResponseDto response = userService.registerUser(user);
        assertNotNull(response);
    }

    @Test
    void testRegisterUserInvalidEmail() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setPhone("9876543210");
        user.setPassword("strongPassword");
        user.setName("Test User");

        assertThrows(InvalidCredentialsException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUserInvalidPhone() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("123");
        user.setPassword("strongPassword");
        user.setName("Test User");

        assertThrows(InvalidCredentialsException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUserEmptyName() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhone("9876543210");
        user.setPassword("strongPassword");
        user.setName("");

        assertThrows(InvalidCredentialsException.class, () -> userService.registerUser(user));
    }
}
