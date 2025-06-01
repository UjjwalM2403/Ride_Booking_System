package com.abes.dao;

import com.abes.dto.Driver;
import com.abes.enums.UserRole;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DriverDaoImplTest {

    private DriverDaoImpl driverDao;

    @BeforeAll
    void setup() {
        driverDao = new DriverDaoImpl();
    }

    @BeforeEach
    void resetStaticFields() {
        try {
            Field driversField = DriverDaoImpl.class.getDeclaredField("drivers");
            driversField.setAccessible(true);
            ((java.util.Map<Long, Driver>) driversField.get(null)).clear();

            Field nextIdField = DriverDaoImpl.class.getDeclaredField("nextId");
            nextIdField.setAccessible(true);
            nextIdField.set(null, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveDriver() {
        Driver driver = new Driver(null, "John Driver", "john.driver@example.com", "1234567890", "LIC12345");
        Driver savedDriver = driverDao.save(driver);
        assertNotNull(savedDriver.getDriverId());
        assertEquals("John Driver", savedDriver.getName());
    }

    @Test
    void testFindById() {
        Driver driver = new Driver(null, "Jane Driver", "jane.driver@example.com", "0987654321", "LIC54321");
        driverDao.save(driver);

        Driver found = driverDao.findById(driver.getDriverId());
        assertNotNull(found);
        assertEquals("jane.driver@example.com", found.getEmail());
    }

    @Test
    void testFindByEmail() {
        Driver driver = new Driver(null, "Alice Driver", "alice.driver@example.com", "1112223333", "LIC11111");
        driverDao.save(driver);

        Driver found = driverDao.findByEmail("alice.driver@example.com");
        assertNotNull(found);
        assertEquals("Alice Driver", found.getName());

        Driver notFound = driverDao.findByEmail("not.exist@example.com");
        assertNull(notFound);
    }

    @Test
    void testFindAvailableDrivers() {
        Driver driver1 = new Driver(null, "Available Driver", "available@example.com", "1111111111", "LIC22222");
        driver1.setIsAvailable(true);
        driverDao.save(driver1);

        Driver driver2 = new Driver(null, "Unavailable Driver", "unavailable@example.com", "2222222222", "LIC33333");
        driver2.setIsAvailable(false);
        driverDao.save(driver2);

        List<Driver> availableDrivers = driverDao.findAvailableDrivers();
        assertEquals(1, availableDrivers.size());
        assertEquals("Available Driver", availableDrivers.get(0).getName());
    }

    @Test
    void testFindAll() {
        driverDao.save(new Driver(null, "Driver1", "driver1@example.com", "3333333333", "LIC44444"));
        driverDao.save(new Driver(null, "Driver2", "driver2@example.com", "4444444444", "LIC55555"));

        List<Driver> allDrivers = driverDao.findAll();
        assertEquals(2, allDrivers.size());
    }

    @Test
    void testUpdateDriver() {
        Driver driver = new Driver(null, "Bob Driver", "bob.driver@example.com", "5555555555", "LIC66666");
        driverDao.save(driver);

        driver.setName("Bob Driver Updated");
        Driver updated = driverDao.update(driver);

        assertNotNull(updated);
        assertEquals("Bob Driver Updated", updated.getName());

        Driver nonExisting = new Driver(999L, "Non Exist", "no@example.com", "0000000000", "LIC00000");
        assertNull(driverDao.update(nonExisting));
    }

    @Test
    void testDeleteDriver() {
        Driver driver = new Driver(null, "Eve Driver", "eve.driver@example.com", "6666666666", "LIC77777");
        driverDao.save(driver);

        boolean deleted = driverDao.delete(driver.getDriverId());
        assertTrue(deleted);

        Driver shouldBeNull = driverDao.findById(driver.getDriverId());
        assertNull(shouldBeNull);

        assertFalse(driverDao.delete(999L));
    }

}
