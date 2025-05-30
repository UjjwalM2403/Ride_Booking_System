package com.abes.dao;

import com.abes.dto.Driver;
import java.util.List;

public interface DriverDao {
    Driver save(Driver driver);
    Driver findById(Long id);
    Driver findByEmail(String email);
    List<Driver> findAvailableDrivers();
    List<Driver> findAll();
    Driver update(Driver driver);
    boolean delete(Long id);
}