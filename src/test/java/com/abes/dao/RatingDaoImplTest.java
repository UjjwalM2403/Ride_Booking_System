package com.abes.dao;

import com.abes.dto.Rating;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingDaoImplTest {

    private RatingDaoImpl ratingDao;

    @BeforeAll
    void setup() {
        ratingDao = new RatingDaoImpl();
    }

    @BeforeEach
    void resetStaticFields() {
        try {
            Field ratingsField = RatingDaoImpl.class.getDeclaredField("ratings");
            ratingsField.setAccessible(true);
            ((java.util.Map<Long, Rating>) ratingsField.get(null)).clear();

            Field nextIdField = RatingDaoImpl.class.getDeclaredField("nextId");
            nextIdField.setAccessible(true);
            nextIdField.set(null, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveRating() {
        Rating rating = new Rating(null, 101L, 1L, 2L, 5, "Excellent ride");
        Rating saved = ratingDao.save(rating);
        assertNotNull(saved.getRatingId());
        assertEquals(5, saved.getUserRating());
        assertEquals("Excellent ride", saved.getFeedback());
    }

    @Test
    void testFindById() {
        Rating rating = new Rating(null, 102L, 3L, 4L, 4, "Good driver");
        ratingDao.save(rating);
        Rating found = ratingDao.findById(rating.getRatingId());
        assertNotNull(found);
        assertEquals(102L, found.getRideId());
    }

    @Test
    void testFindByRideId() {
        Rating rating = new Rating(null, 103L, 5L, 6L, 3, "Average");
        ratingDao.save(rating);
        Rating found = ratingDao.findByRideId(103L);
        assertNotNull(found);
        assertEquals(5L, found.getUserId());

        Rating notFound = ratingDao.findByRideId(999L);
        assertNull(notFound);
    }

    @Test
    void testFindByUserId() {
        ratingDao.save(new Rating(null, 201L, 10L, 20L, 5, "Great"));
        ratingDao.save(new Rating(null, 202L, 10L, 21L, 4, "Nice"));
        ratingDao.save(new Rating(null, 203L, 11L, 22L, 3, "Okay"));

        List<Rating> userRatings = ratingDao.findByUserId(10L);
        assertEquals(2, userRatings.size());

        List<Rating> noRatings = ratingDao.findByUserId(99L);
        assertTrue(noRatings.isEmpty());
    }

    @Test
    void testFindByDriverId() {
        ratingDao.save(new Rating(null, 301L, 40L, 50L, 5, "Perfect"));
        ratingDao.save(new Rating(null, 302L, 41L, 50L, 4, "Good"));
        ratingDao.save(new Rating(null, 303L, 42L, 51L, 2, "Bad"));

        List<Rating> driverRatings = ratingDao.findByDriverId(50L);
        assertEquals(2, driverRatings.size());

        List<Rating> noRatings = ratingDao.findByDriverId(99L);
        assertTrue(noRatings.isEmpty());
    }

    @Test
    void testFindAll() {
        ratingDao.save(new Rating(null, 401L, 60L, 70L, 5, "Excellent"));
        ratingDao.save(new Rating(null, 402L, 61L, 71L, 4, "Good"));

        List<Rating> all = ratingDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdateRating() {
        Rating rating = new Rating(null, 501L, 80L, 90L, 3, "Average");
        ratingDao.save(rating);

        rating.setUserRating(4);
        rating.setFeedback("Better");
        Rating updated = ratingDao.update(rating);

        assertNotNull(updated);
        assertEquals(4, updated.getUserRating());
        assertEquals("Better", updated.getFeedback());

        Rating nonExisting = new Rating(999L, 0L, 0L, 0L, 1, "Non existing");
        assertNull(ratingDao.update(nonExisting));
    }

    @Test
    void testDelete() {
        Rating rating = new Rating(null, 601L, 100L, 110L, 5, "Great");
        ratingDao.save(rating);

        boolean deleted = ratingDao.delete(rating.getRatingId());
        assertTrue(deleted);

        Rating shouldBeNull = ratingDao.findById(rating.getRatingId());
        assertNull(shouldBeNull);

        assertFalse(ratingDao.delete(999L));
    }
}
