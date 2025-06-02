package com.abes.service;

import com.abes.dto.Driver;
import com.abes.dto.Vehicle;
import com.abes.dto.ResponseDto;
import com.abes.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for DriverServiceImpl
 */
public class DriverServiceImplTest {

    DriverServiceImpl driverService = new DriverServiceImpl();

    /**
     * Test case: Register driver with valid data
     */
    @Test
    void testRegisterDriverWithValidData() {
        Driver driver = new Driver();
        driver.setName("Amit Driver");
        driver.setEmail("amit@example.com");
        driver.setPhone("9876543210");
        driver.setLicenseNumber("DL12345");

        Vehicle vehicle = new Vehicle();
        vehicle.setModel("Honda City");
        vehicle.setPlateNumber("UP32AB1234");

        assertDoesNotThrow(() -> {
            ResponseDto response = driverService.registerDriver(driver, vehicle);
            assertNotNull(response);
            assertEquals("Driver registered successfully", response.getMessage());
        });
    }

    /**
     * Test case: Register driver with invalid email
     */
    @Test
    void testRegisterDriverWithInvalidEmail() {
        Driver driver = new Driver();
        driver.setName("Invalid Email");
        driver.setEmail("wrong-email"); // invalid
        driver.setPhone("9876543210");
        driver.setLicenseNumber("DL12345");

        Vehicle vehicle = new Vehicle();

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            driverService.registerDriver(driver, vehicle);
        });

        assertEquals("Invalid email format", exception.getMessage());
    }

    /**
     * Test case: Register driver with empty name
     */
    @Test
    void testRegisterDriverWithEmptyName() {
        Driver driver = new Driver();
        driver.setName(""); // empty name
        driver.setEmail("valid@example.com");
        driver.setPhone("9876543210");
        driver.setLicenseNumber("DL12345");

        Vehicle vehicle = new Vehicle();

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            driverService.registerDriver(driver, vehicle);
        });

        assertEquals("Name cannot be empty", exception.getMessage());
    }

    /**
     * Test case: Register driver with missing license number
     */
    @Test
    void testRegisterDriverWithEmptyLicense() {
        Driver driver = new Driver();
        driver.setName("No License");
        driver.setEmail("nolicense@example.com");
        driver.setPhone("9876543210");
        driver.setLicenseNumber(""); // empty license

        Vehicle vehicle = new Vehicle();

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            driverService.registerDriver(driver, vehicle);
        });

        assertEquals("License number cannot be empty", exception.getMessage());
    }

    /**
     * Test case: Register driver with invalid phone number
     */
    @Test
    void testRegisterDriverWithInvalidPhone() {
        Driver driver = new Driver();
        driver.setName("Bad Phone");
        driver.setEmail("phone@example.com");
        driver.setPhone("123"); // invalid
        driver.setLicenseNumber("DL12345");

        Vehicle vehicle = new Vehicle();

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            driverService.registerDriver(driver, vehicle);
        });

        assertEquals("Invalid phone number", exception.getMessage());
    }
}
