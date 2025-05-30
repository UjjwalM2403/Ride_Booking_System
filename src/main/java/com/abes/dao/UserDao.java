package com.abes.dao;

import com.abes.dto.User;
import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    User update(User user);
    boolean delete(Long id);
}