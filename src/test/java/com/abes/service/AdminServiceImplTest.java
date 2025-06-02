package com.abes.service;

import com.abes.dto.Admin;
import com.abes.dto.ResponseDto;
import com.abes.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AdminServiceImpl using JUnit 5
 */
public class AdminServiceImplTest {

    // Creating an object of AdminServiceImpl to test its methods
    AdminServiceImpl adminService = new AdminServiceImpl();

    /**
     * Test case to verify successful registration of an admin
     * with valid email, phone, and password
     */
    @Test
    void testRegisterAdminWithValidData() {
        Admin admin = new Admin();
        admin.setName("John Admin");
        admin.setEmail("admin@example.com");  // valid email
        admin.setPhone("9876543210");         // valid phone
        admin.setPassword("StrongPass@123");  // valid password

        // Should not throw any exception and return a successful response
        assertDoesNotThrow(() -> {
            ResponseDto response = adminService.registerAdmin(admin);
            assertNotNull(response);  // response should not be null
            assertEquals("Admin registered successfully", response.getMessage());
        });
    }

    /**
     * Test case to verify that registration fails
     * when the admin email is invalid
     */
    @Test
    void testRegisterAdminWithInvalidEmail() {
        Admin admin = new Admin();
        admin.setName("John Admin");
        admin.setEmail("invalid-email");      // invalid email
        admin.setPhone("9876543210");
        admin.setPassword("StrongPass@123");

        // Should throw InvalidCredentialsException with a specific message
        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            adminService.registerAdmin(admin);
        });

        assertEquals("Invalid email format", exception.getMessage());
    }

    /**
     * Test case to check that invalid phone number is rejected
     */
    @Test
    void testRegisterAdminWithInvalidPhone() {
        Admin admin = new Admin();
        admin.setName("John Admin");
        admin.setEmail("admin@example.com");
        admin.setPhone("123");                // invalid phone
        admin.setPassword("StrongPass@123");

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            adminService.registerAdmin(admin);
        });

        assertEquals("Invalid phone number", exception.getMessage());
    }

    /**
     * Test case to ensure that weak passwords are not accepted
     */
    @Test
    void testRegisterAdminWithWeakPassword() {
        Admin admin = new Admin();
        admin.setName("John Admin");
        admin.setEmail("admin@example.com");
        admin.setPhone("9876543210");
        admin.setPassword("123");             // weak password

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            adminService.registerAdmin(admin);
        });

        assertTrue(exception.getMessage().contains("Password must be"));
    }
}

