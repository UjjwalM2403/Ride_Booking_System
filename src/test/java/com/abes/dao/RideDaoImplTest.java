package com.abes.dao;

import com.abes.dto.Location;
import com.abes.dto.Ride;
import com.abes.enums.RideStatus;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideDaoImplTest {

    private RideDaoImpl rideDao;

    @BeforeAll
    void setup() {
        rideDao = new RideDaoImpl();
    }

    @BeforeEach
    void resetStaticData() {
        try {
            Field ridesField = RideDaoImpl.class.getDeclaredField("rides");
            ridesField.setAccessible(true);
            ((Map<Long, Ride>) ridesField.get(null)).clear();

            Field nextIdField = RideDaoImpl.class.getDeclaredField("nextId");
            nextIdField.setAccessible(true);
            nextIdField.set(null, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveAndFindById() {
        Location pickup = new Location(28.7041, 77.1025, "Delhi");
        Location drop = new Location(19.0760, 72.8777, "Mumbai");
        Ride ride = new Ride(null, 101L, pickup, drop);
        Ride saved = rideDao.save(ride);

        assertNotNull(saved.getRideId());
        Ride found = rideDao.findById(saved.getRideId());
        assertNotNull(found);
        assertEquals(101L, found.getUserId());
    }

    @Test
    void testFindByUserId() {
        Ride ride1 = new Ride(null, 201L, new Location(1.1, 1.2), new Location(2.1, 2.2));
        Ride ride2 = new Ride(null, 201L, new Location(3.1, 3.2), new Location(4.1, 4.2));
        rideDao.save(ride1);
        rideDao.save(ride2);

        List<Ride> userRides = rideDao.findByUserId(201L);
        assertEquals(2, userRides.size());
    }

    @Test
    void testFindByDriverId() {
        Ride ride = new Ride(null, 301L, new Location(1.0, 2.0), new Location(3.0, 4.0));
        ride.setDriverId(401L);
        rideDao.save(ride);

        List<Ride> driverRides = rideDao.findByDriverId(401L);
        assertEquals(1, driverRides.size());
        assertEquals(401L, driverRides.get(0).getDriverId());
    }

    @Test
    void testFindAll() {
        rideDao.save(new Ride(null, 1L, new Location(0, 0), new Location(1, 1)));
        rideDao.save(new Ride(null, 2L, new Location(2, 2), new Location(3, 3)));

        List<Ride> all = rideDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdate() {
        Ride ride = new Ride(null, 101L, new Location(0.0, 0.0), new Location(1.0, 1.0));
        rideDao.save(ride);

        ride.setStatus(RideStatus.IN_PROGRESS);
        Ride updated = rideDao.update(ride);

        assertNotNull(updated);
        assertEquals(RideStatus.IN_PROGRESS, updated.getStatus());
    }

    @Test
    void testDelete() {
        Ride ride = new Ride(null, 501L, new Location(5, 5), new Location(6, 6));
        rideDao.save(ride);

        boolean deleted = rideDao.delete(ride.getRideId());
        assertTrue(deleted);
        assertNull(rideDao.findById(ride.getRideId()));
    }
}
