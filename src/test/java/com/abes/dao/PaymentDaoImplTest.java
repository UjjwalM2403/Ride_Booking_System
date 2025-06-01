package com.abes.dao;

import com.abes.dto.Payment;
import com.abes.enums.PaymentMethod;
import com.abes.enums.PaymentStatus;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentDaoImplTest {

    private PaymentDaoImpl paymentDao;

    @BeforeAll
    void setup() {
        paymentDao = new PaymentDaoImpl();
    }

    @BeforeEach
    void resetState() throws Exception {
        Field paymentsField = PaymentDaoImpl.class.getDeclaredField("payments");
        paymentsField.setAccessible(true);
        ((java.util.Map<Long, Payment>) paymentsField.get(null)).clear();

        Field nextIdField = PaymentDaoImpl.class.getDeclaredField("nextId");
        nextIdField.setAccessible(true);
        nextIdField.set(null, 1L);
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment(null, 101L, 500.0, PaymentMethod.CARD);
        Payment saved = paymentDao.save(payment);

        assertNotNull(saved.getPaymentId());
        assertEquals(101L, saved.getRideId());
        assertEquals(500.0, saved.getAmount());
        assertEquals(PaymentMethod.CARD, saved.getMethod());
        assertEquals(PaymentStatus.PENDING, saved.getStatus());
        assertNotNull(saved.getTimestamp());
    }

    @Test
    void testFindById() {
        Payment payment = new Payment(null, 102L, 250.0, PaymentMethod.UPI);
        paymentDao.save(payment);

        Payment found = paymentDao.findById(payment.getPaymentId());
        assertNotNull(found);
        assertEquals(102L, found.getRideId());
    }

    @Test
    void testFindByRideId() {
        Payment payment = new Payment(null, 103L, 100.0, PaymentMethod.CASH);
        paymentDao.save(payment);

        Payment found = paymentDao.findByRideId(103L);
        assertNotNull(found);
        assertEquals(100.0, found.getAmount());

        Payment notFound = paymentDao.findByRideId(999L);
        assertNull(notFound);
    }

    @Test
    void testFindAll() {
        paymentDao.save(new Payment(null, 201L, 30.0, PaymentMethod.WALLET));
        paymentDao.save(new Payment(null, 202L, 45.0, PaymentMethod.CARD));

        List<Payment> all = paymentDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdatePayment() {
        Payment payment = new Payment(null, 301L, 80.0, PaymentMethod.CARD);
        paymentDao.save(payment);

        payment.setAmount(90.0);
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment updated = paymentDao.update(payment);

        assertNotNull(updated);
        assertEquals(90.0, updated.getAmount());
        assertEquals(PaymentStatus.COMPLETED, updated.getStatus());

        Payment nonExisting = new Payment(999L, 0L, 0.0, PaymentMethod.CASH);
        assertNull(paymentDao.update(nonExisting));
    }

    @Test
    void testDeletePayment() {
        Payment payment = new Payment(null, 401L, 60.0, PaymentMethod.UPI);
        paymentDao.save(payment);

        boolean deleted = paymentDao.delete(payment.getPaymentId());
        assertTrue(deleted);

        Payment shouldBeNull = paymentDao.findById(payment.getPaymentId());
        assertNull(shouldBeNull);

        assertFalse(paymentDao.delete(999L));
    }
}
