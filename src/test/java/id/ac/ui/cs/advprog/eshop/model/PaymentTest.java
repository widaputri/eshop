package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentValidVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment("1", "Voucher", "PENDING", paymentData);

        assertEquals("1", payment.getId());
        assertEquals("Voucher", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentInvalidVoucherCode() {
        paymentData.put("voucherCode", "INVALIDCODE123");
        payment = new Payment("2", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithValidBankTransfer() {
        paymentData.put("bankName", "Bank A");
        paymentData.put("referenceCode", "12345678");
        payment = new Payment("3", "Bank Transfer", "PENDING", paymentData);

        assertEquals("3", payment.getId());
        assertEquals("Bank Transfer", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Bank A", payment.getPaymentData().get("bankName"));
        assertEquals("12345678", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testCreatePaymentWithMissingBankName() {
        paymentData.put("referenceCode", "12345678");
        payment = new Payment("4", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithMissingReferenceCode() {
        paymentData.put("bankName", "Bank A");
        payment = new Payment("5", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToSuccess() {
        payment = new Payment("6", "Bank Transfer", "PENDING", new HashMap<>());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        payment = new Payment("7", "Voucher", "PENDING", new HashMap<>());
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeTooShort() {
        paymentData.put("voucherCode", "ESHOP123"); // Hanya 9 karakter
        payment = new Payment("8", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeTooLong() {
        paymentData.put("voucherCode", "ESHOP1234ABC567890"); // 18 karakter
        payment = new Payment("9", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeWithoutESHOPPrefix() {
        paymentData.put("voucherCode", "SHOP1234ABC5678"); // Tanpa "ESHOP"
        payment = new Payment("10", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeWithoutEightDigits() {
        paymentData.put("voucherCode", "ESHOPABCDABCDABCD"); // Tidak ada 8 angka
        payment = new Payment("11", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeIsEmpty() {
        paymentData.put("voucherCode", "");
        payment = new Payment("12", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeIsNull() {
        paymentData.put("voucherCode", null);
        payment = new Payment("13", "Voucher", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyBankName() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "12345678");
        payment = new Payment("14", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyReferenceCode() {
        paymentData.put("bankName", "Bank A");
        paymentData.put("referenceCode", "");
        payment = new Payment("15", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithoutBankNameKey() {
        paymentData.put("referenceCode", "12345678");
        payment = new Payment("16", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithoutReferenceCodeKey() {
        paymentData.put("bankName", "Bank A");
        payment = new Payment("17", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithBothBankFieldsEmpty() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");
        payment = new Payment("18", "Bank Transfer", "PENDING", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}
