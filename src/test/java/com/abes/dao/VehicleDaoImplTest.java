package com.abes.dao;

import com.abes.dto.Vehicle;
import com.abes.enums.VehicleType;
import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDaoImplTest {

    private VehicleDaoImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new VehicleDaoImpl();

        // Reset the static fields using reflection
        Field vehiclesField = VehicleDaoImpl.class.getDeclaredField("vehicles");
        vehiclesField.setAccessible(true);
        ((Map<Long, Vehicle>) vehiclesField.get(null)).clear();

        Field nextIdField = VehicleDaoImpl.class.getDeclaredField("nextId");
        nextIdField.setAccessible(true);
        nextIdField.set(null, 1L);
    }

    @Test
    void testSaveAndFindById() {
        Vehicle vehicle = new Vehicle(null, "UP32AB1234", VehicleType.SEDAN, "Honda City", "White", 4, 101L);
        Vehicle saved = dao.save(vehicle);

        assertNotNull(saved.getVehicleId());
        assertEquals("UP32AB1234", saved.getPlateNumber());

        Vehicle fetched = dao.findById(saved.getVehicleId());
        assertEquals(saved, fetched);
    }

    @Test
    void testFindByDriverId() {
        Vehicle vehicle = new Vehicle(null, "DL01CD5678", VehicleType.SUV, "Hyundai Creta", "Black", 5, 202L);
        dao.save(vehicle);

        Vehicle found = dao.findByDriverId(202L);
        assertNotNull(found);
        assertEquals("DL01CD5678", found.getPlateNumber());
    }

    @Test
    void testFindAll() {
        dao.save(new Vehicle(null, "MH12XY7890", VehicleType.HATCHBACK, "Swift", "Red", 4, 303L));
        dao.save(new Vehicle(null, "KA05ZZ1111", VehicleType.SEDAN, "Verna", "Blue", 4, 404L));

        List<Vehicle> all = dao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdate() {
        Vehicle vehicle = new Vehicle(null, "RJ14GH2345", VehicleType.SEDAN, "Ciaz", "Grey", 4, 505L);
        Vehicle saved = dao.save(vehicle);

        saved.setColor("Silver");
        Vehicle updated = dao.update(saved);

        assertNotNull(updated);
        assertEquals("Silver", updated.getColor());
    }

    @Test
    void testDelete() {
        Vehicle vehicle = new Vehicle(null, "WB19PQ4567", VehicleType.SUV, "Scorpio", "Black", 7, 606L);
        Vehicle saved = dao.save(vehicle);

        boolean deleted = dao.delete(saved.getVehicleId());
        assertTrue(deleted);

        assertNull(dao.findById(saved.getVehicleId()));
    }
}
