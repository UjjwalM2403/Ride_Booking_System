package com.abes.dao;

import com.abes.dto.User;
import java.util.*;

public class UserDaoImpl implements UserDao {
    private static Map<Long, User> users = new HashMap<>();
    private static Long nextId = 1L;
    
    @Override
    public User save(User user) {
        user.setUserId(nextId++);
        users.put(user.getUserId(), user);
        return user;
    }
    
    @Override
    public User findById(Long id) {
        return users.get(id);
    }
    
    @Override
    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public User update(User user) {
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            return user;
        }
        return null;
    }
    
    @Override
    public boolean delete(Long id) {
        return users.remove(id) != null;
    }
}