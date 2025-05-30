package com.abes.dao;

import com.abes.dto.Ride;
import java.util.*;

public class RideDaoImpl implements RideDao {
    private static Map<Long, Ride> rides = new HashMap<>();
    private static Long nextId = 1L;
    
    
    @Override
    public Ride findById(Long id) {
        return rides.get(id);
    }
    
    @Override
    public Ride save(Ride ride) {
    	ride.setRideId(nextId++);
    	rides.put(ride.getRideId(), ride);
    	return ride;
    }
    @Override
    public List<Ride> findByUserId(Long userId) {
        List<Ride> userRides = new ArrayList<>();
        for (Ride ride : rides.values()) {
            if (ride.getUserId().equals(userId)) {
                userRides.add(ride);
            }
        }
        return userRides;
    }
    
    @Override
    public List<Ride> findByDriverId(Long driverId) {
        List<Ride> driverRides = new ArrayList<>();
        for (Ride ride : rides.values()) {
            if (ride.getDriverId() != null && ride.getDriverId().equals(driverId)) {
                driverRides.add(ride);
            }
        }
        return driverRides;
    }
    
    @Override
    public List<Ride> findAll() {
        return new ArrayList<>(rides.values());
    }
    
    @Override
    public Ride update(Ride ride) {
        if (rides.containsKey(ride.getRideId())) {
            rides.put(ride.getRideId(), ride);
            return ride;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return rides.remove(id) != null;
    }
}