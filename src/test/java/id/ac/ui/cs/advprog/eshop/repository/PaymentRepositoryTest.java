package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment samplePayment1;
    private Payment samplePayment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        samplePayment1 = new Payment("1", "Voucher", "PENDING", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "Bank A");
        paymentData2.put("referenceCode", "12345678");
        samplePayment2 = new Payment("2", "Bank Transfer", "PENDING", paymentData2);
    }

    @Test
    void testCreatePayment() {
        Payment savedPayment = paymentRepository.create(samplePayment1);
        assertNotNull(savedPayment);
        assertEquals("1", savedPayment.getId());
        assertEquals("Voucher", savedPayment.getMethod());
    }

    @Test
    void testFindByIdExistingPayment() {
        paymentRepository.create(samplePayment1);
        Payment retrievedPayment = paymentRepository.findById("1");
        assertNotNull(retrievedPayment);
        assertEquals("1", retrievedPayment.getId());
    }

    @Test
    void testFindByIdNonExistingPayment() {
        Payment retrievedPayment = paymentRepository.findById("999");
        assertNull(retrievedPayment);
    }

    @Test
    void testFindAllPayments() {
        paymentRepository.create(samplePayment1);
        paymentRepository.create(samplePayment2);
        Iterator<Payment> paymentIterator = paymentRepository.findAll();
        assertTrue(paymentIterator.hasNext());
        int count = 0;
        while (paymentIterator.hasNext()) {
            paymentIterator.next();
            count++;
        }

        assertNotNull(paymentIterator);
        assertEquals(2, count);
    }

    @Test
    void testUpdateExistingPayment() {
        paymentRepository.create(samplePayment1);
        Payment updatedPayment = new Payment("1", "Voucher", "SUCCESS", samplePayment1.getPaymentData());
        Payment result = paymentRepository.update("1", updatedPayment);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testUpdateNonExistingPayment() {
        Payment updatedPayment = new Payment("999", "Voucher", "SUCCESS", new HashMap<>());
        Payment result = paymentRepository.update("999", updatedPayment);

        assertNull(result);
    }

    @Test
    void testDeleteExistingPayment() {
        paymentRepository.create(samplePayment1);
        paymentRepository.delete("1");
        assertNull(paymentRepository.findById("1"));
    }

    @Test
    void testDeleteNonExistingPayment() {
        assertDoesNotThrow(() -> paymentRepository.delete("999"));
    }
}