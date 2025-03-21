package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testPaymentSettersAndGetters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "Voucher", "PENDING", paymentData);

        // Verify getters
        assertEquals("1", payment.getId());
        assertEquals("Voucher", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));

        // Modify and verify setters
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }
}
