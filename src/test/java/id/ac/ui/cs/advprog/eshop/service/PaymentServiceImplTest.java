package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Spy
    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Payment payment;
    private Map<String, String> paymentData;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create products
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("product-1");
        product1.setProductName("Product One");
        product1.setProductQuantity(10);
        Product product2 = new Product();
        product2.setProductId("product-2");
        product2.setProductName("Product Two");
        product2.setProductQuantity(20);
        products.add(product1);
        products.add(product2);

        // Mock product repository to return products
        when(productRepository.findAll()).thenReturn(products.iterator());

        // Create an order with products
        order = new Order("order-1", products, System.currentTimeMillis(), "author-1");

        // Initialize payment data
        paymentData = new HashMap<>();
        payment = new Payment("payment-1", "Voucher", "SUCCESS", paymentData);
    }

    @Test
    void testAddPaymentWithValidVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("SUCCESS", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithInvalidVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", "INVALID1234CODE");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithNullVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", null);
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithShortVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", "ESHOP12345"); // Too short
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithNonEshopVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", "STORE1234ABC5678"); // Doesn't start with ESHOP
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithInsufficientDigitsVoucherCode() {
        // Arrange
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJKL"); // No digits
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Voucher", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransferValidData() {
        // Arrange
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "REF123456");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Bank Transfer", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("PENDING", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransferInvalidData() {
        // Arrange
        paymentData.put("bankName", ""); // Empty bank name
        paymentData.put("referenceCode", "REF123456");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Bank Transfer", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransferMissingBankName() {
        // Arrange
        paymentData.clear();
        paymentData.put("referenceCode", "REF123456");
        // No bankName
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Bank Transfer", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransferMissingReferenceCode() {
        // Arrange
        paymentData.clear();
        paymentData.put("bankName", "Bank ABC");
        // No referenceCode
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Bank Transfer", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithBankTransferEmptyReferenceCode() {
        // Arrange
        paymentData.clear();
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "Bank Transfer", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("REJECTED", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testAddPaymentWithUnknownMethod() {
        // Arrange
        paymentData.put("someData", "someValue");
        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = paymentService.addPayment(order, "UnknownMethod", paymentData);

        // Assert
        assertNotNull(createdPayment);
        assertEquals("PENDING", createdPayment.getStatus());
        verify(paymentRepository, times(1)).create(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToSuccess() {
        // Arrange
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-1", "Voucher", "SUCCESS", paymentData);

        // Mock the behavior we expect inside setStatus method
        when(paymentRepository.findById("payment-1")).thenReturn(payment);
        when(orderRepository.findById("order-1")).thenReturn(order);

        // Mock the mapping lookup
        doReturn("order-1").when(paymentService).getOrderIdForPayment("payment-1");

        // Act
        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        // Assert
        assertNotNull(updatedPayment);
        assertEquals("SUCCESS", updatedPayment.getStatus());
        assertEquals("SUCCESS", order.getStatus()); // Direct check on order object
        verify(paymentRepository, times(1)).update("payment-1", payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToRejected() {
        // Arrange
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-1", "Voucher", "SUCCESS", paymentData);

        // Mock the behavior we expect inside setStatus method
        when(paymentRepository.findById("payment-1")).thenReturn(payment);
        when(orderRepository.findById("order-1")).thenReturn(order);

        // Mock the mapping lookup
        doReturn("order-1").when(paymentService).getOrderIdForPayment("payment-1");

        // Act
        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");

        // Assert
        assertNotNull(updatedPayment);
        assertEquals("REJECTED", updatedPayment.getStatus());
        assertEquals("FAILED", order.getStatus()); // Direct check on order object
        verify(paymentRepository, times(1)).update("payment-1", payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusWithOrderIdNotFound() {
        // Arrange
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-1", "Voucher", "SUCCESS", paymentData);

        // Mock the behavior to return null order ID
        doReturn(null).when(paymentService).getOrderIdForPayment("payment-1");

        // Act
        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        // Assert
        assertNotNull(updatedPayment);
        assertEquals("SUCCESS", updatedPayment.getStatus());
        verify(paymentRepository, times(1)).update("payment-1", payment);
        // OrderRepository should not be called when orderId is null
        verify(orderRepository, never()).findById(anyString());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testSetStatusWithOrderNotFound() {
        // Arrange
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-1", "Voucher", "SUCCESS", paymentData);

        // Mock the behavior to return an orderId but null order
        doReturn("order-1").when(paymentService).getOrderIdForPayment("payment-1");
        when(orderRepository.findById("order-1")).thenReturn(null);

        // Act
        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        // Assert
        assertNotNull(updatedPayment);
        assertEquals("SUCCESS", updatedPayment.getStatus());
        verify(paymentRepository, times(1)).update("payment-1", payment);
        verify(orderRepository, times(1)).findById("order-1");
        // OrderRepository save should not be called when order is null
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "INVALID_STATUS");
        });
    }

    @Test
    void testGetPaymentById() {
        // Arrange
        when(paymentRepository.findById("payment-1")).thenReturn(payment);

        // Act
        Payment foundPayment = paymentService.getPayment("payment-1");

        // Assert
        assertNotNull(foundPayment);
        assertEquals("payment-1", foundPayment.getId());
        verify(paymentRepository, times(1)).findById("payment-1");
    }

    @Test
    void testGetAllPayments() {
        // Arrange
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findAll()).thenReturn(payments.iterator());

        // Act
        List<Payment> allPayments = paymentService.getAllPayments();

        // Assert
        assertNotNull(allPayments);
        assertEquals(1, allPayments.size());
        assertEquals("payment-1", allPayments.get(0).getId());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPaymentsEmpty() {
        // Arrange
        when(paymentRepository.findAll()).thenReturn(Collections.emptyIterator());

        // Act
        List<Payment> allPayments = paymentService.getAllPayments();

        // Assert
        assertNotNull(allPayments);
        assertTrue(allPayments.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }
}