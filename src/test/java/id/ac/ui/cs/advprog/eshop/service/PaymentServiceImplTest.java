package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPayment_VoucherCode_Valid() {
        Order order = mock(Order.class);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "Voucher", "PENDING", paymentData);
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "Voucher", paymentData);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPayment_VoucherCode_Invalid() {
        Order order = mock(Order.class);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE");

        Payment payment = new Payment("2", "Voucher", "PENDING", paymentData);
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "Voucher", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPayment_BankTransfer_Valid() {
        Order order = mock(Order.class);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "123456");

        Payment payment = new Payment("3", "Bank Transfer", "PENDING", paymentData);
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "Bank Transfer", paymentData);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testAddPayment_BankTransfer_Invalid() {
        Order order = mock(Order.class);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("4", "Bank Transfer", "PENDING", paymentData);
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "Bank Transfer", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatus_Success() {
        Payment payment = mock(Payment.class);
        Order order = mock(Order.class);
        when(payment.getStatus()).thenReturn("SUCCESS");
        when(payment.getId()).thenReturn("5");

        when(paymentRepository.findById("5")).thenReturn(payment);
        doNothing().when(payment).setStatus("SUCCESS");

        Payment result = paymentService.setStatus(payment, "SUCCESS");
        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testSetStatus_Rejected() {
        Payment payment = mock(Payment.class);
        Order order = mock(Order.class);
        when(payment.getStatus()).thenReturn("REJECTED");
        when(payment.getId()).thenReturn("6");

        when(paymentRepository.findById("6")).thenReturn(payment);
        doNothing().when(payment).setStatus("REJECTED");

        Payment result = paymentService.setStatus(payment, "REJECTED");
        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("7", "Voucher", "SUCCESS", new HashMap<>());
        when(paymentRepository.findById("7")).thenReturn(payment);

        Payment result = paymentService.getPayment("7");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(
                new Payment("8", "Voucher", "SUCCESS", new HashMap<>()),
                new Payment("9", "Bank Transfer", "PENDING", new HashMap<>())
        );
        Iterator<Payment> paymentIterator = payments.iterator();
        when(paymentRepository.findAll()).thenReturn(paymentIterator);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("SUCCESS", result.get(0).getStatus());
        assertEquals("PENDING", result.get(1).getStatus());
    }
}
