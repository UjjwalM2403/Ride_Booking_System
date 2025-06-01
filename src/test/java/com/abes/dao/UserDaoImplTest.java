package com.abes.dao;

import com.abes.dto.User;
import com.abes.enums.UserRole;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoImplTest {

    private UserDaoImpl userDao;

    @BeforeAll
    void setup() {
        userDao = new UserDaoImpl();
    }

    @BeforeEach
    void beforeEach() {
        // Clear the internal state before each test since it's static
        // We need reflection to reset static fields or recreate instance
        // Here, we create a new instance but static map will persist
        // So, workaround: reset via reflection:

        try {
        	java.lang.reflect.Field usersField = UserDaoImpl.class.getDeclaredField("users");
            usersField.setAccessible(true);
            ((java.util.Map<Long, User>) usersField.get(null)).clear();

            java.lang.reflect.Field nextIdField = UserDaoImpl.class.getDeclaredField("nextId");
            nextIdField.setAccessible(true);
            nextIdField.set(null, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveUser() {
        User user = new User(null, "John Doe", "john@example.com", "1234567890", "password", UserRole.USER);
        User savedUser = userDao.save(user);
        assertNotNull(savedUser.getUserId());
        assertEquals("John Doe", savedUser.getName());
    }

    @Test
    void testFindById() {
        User user = new User(null, "Jane Smith", "jane@example.com", "0987654321", "password", UserRole.USER);
        userDao.save(user);

        User found = userDao.findById(user.getUserId());
        assertNotNull(found);
        assertEquals("jane@example.com", found.getEmail());
    }

    @Test
    void testFindByEmail() {
        User user = new User(null, "Alice", "alice@example.com", "1112223333", "password", UserRole.USER);
        userDao.save(user);

        User found = userDao.findByEmail("alice@example.com");
        assertNotNull(found);
        assertEquals("Alice", found.getName());

        User notFound = userDao.findByEmail("notexist@example.com");
        assertNull(notFound);
    }

    @Test
    void testFindAll() {
        userDao.save(new User(null, "User1", "user1@example.com", "111", "pass", UserRole.USER));
        userDao.save(new User(null, "User2", "user2@example.com", "222", "pass", UserRole.USER));

        List<User> allUsers = userDao.findAll();
        assertEquals(2, allUsers.size());
    }

    @Test
    void testUpdateUser() {
        User user = new User(null, "Bob", "bob@example.com", "333", "pass", UserRole.USER);
        userDao.save(user);

        user.setName("Bob Updated");
        User updated = userDao.update(user);

        assertNotNull(updated);
        assertEquals("Bob Updated", updated.getName());

        // Try to update user not in DB
        User nonExisting = new User(999L, "Non Exist", "no@example.com", "000", "pass", UserRole.USER);
        assertNull(userDao.update(nonExisting));
    }

    @Test
    void testDeleteUser() {
        User user = new User(null, "Eve", "eve@example.com", "444", "pass", UserRole.USER);
        userDao.save(user);

        boolean deleted = userDao.delete(user.getUserId());
        assertTrue(deleted);

        User shouldBeNull = userDao.findById(user.getUserId());
        assertNull(shouldBeNull);

        // Delete non-existing id
        assertFalse(userDao.delete(999L));
    }
}
