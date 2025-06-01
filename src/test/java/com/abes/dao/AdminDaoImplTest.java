package com.abes.dao;

import com.abes.dto.Admin;
import com.abes.dto.Driver;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminDaoImplTest {

    private AdminDaoImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new AdminDaoImpl();

        // Reset static fields using reflection
        Field adminsField = AdminDaoImpl.class.getDeclaredField("admins");
        adminsField.setAccessible(true);
        ((Map<Long, Admin>) adminsField.get(null)).clear();

        Field driversField = AdminDaoImpl.class.getDeclaredField("drivers");
        driversField.setAccessible(true);
        ((Map<Long, Driver>) driversField.get(null)).clear();

        Field nextIdField = AdminDaoImpl.class.getDeclaredField("nextId");
        nextIdField.setAccessible(true);
        nextIdField.set(null, 1L);
    }

    @Test
    void testSaveAndFindById() {
        Admin admin = new Admin(null, "Alice", "alice@abes.com", "9999999999", "pass123");
        Admin saved = dao.save(admin);

        assertNotNull(saved.getAdminId());
        assertEquals("Alice", saved.getName());

        Admin found = dao.findById(saved.getAdminId());
        assertEquals(saved, found);
    }

    @Test
    void testFindByEmail() {
        Admin admin = new Admin(null, "Bob", "bob@abes.com", "8888888888", "pass456");
        dao.save(admin);

        Admin found = dao.findByEmail("bob@abes.com");
        assertNotNull(found);
        assertEquals("Bob", found.getName());
    }

    @Test
    void testFindAll() {
        dao.save(new Admin(null, "Admin1", "a1@abes.com", "123", "p1"));
        dao.save(new Admin(null, "Admin2", "a2@abes.com", "456", "p2"));

        List<Admin> admins = dao.findAll();
        assertEquals(2, admins.size());
    }

    @Test
    void testUpdate() {
        Admin admin = new Admin(null, "Charlie", "charlie@abes.com", "111", "oldPass");
        Admin saved = dao.save(admin);

        saved.setPhone("0000000000");
        Admin updated = dao.update(saved);

        assertNotNull(updated);
        assertEquals("0000000000", updated.getPhone());
    }

    @Test
    void testDelete() {
        Admin admin = new Admin(null, "David", "david@abes.com", "222", "delete");
        Admin saved = dao.save(admin);

        boolean deleted = dao.delete(saved.getAdminId());
        assertTrue(deleted);

        assertNull(dao.findById(saved.getAdminId()));
    }

    @Test
    void testFindAvailableDrivers() throws Exception {
        // Add an available driver to the static map using reflection
        Field driversField = AdminDaoImpl.class.getDeclaredField("drivers");
        driversField.setAccessible(true);
        Map<Long, Driver> driversMap = (Map<Long, Driver>) driversField.get(null);
        Driver driver = new Driver(1L, "Raj", "raj@abes.com", "123456", "LIC123");
        driver.setIsAvailable(true);
        driversMap.put(1L, driver);

        List<Driver> availableDrivers = dao.findAvailableDrivers();
        assertEquals(1, availableDrivers.size());
        assertTrue(availableDrivers.get(0).getIsAvailable());
    }
}
