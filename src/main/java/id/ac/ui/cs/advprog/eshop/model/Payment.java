package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment(status);
    }

    private String validatePayment(String initialStatus) {
        return null;
    }

    private boolean isValidVoucherCode(String code) {
        return false;
    }

    private boolean isInvalidBankTransfer(Map<String, String> paymentData) {
        return false;
    }

    public void setStatus(String status) {

    }
}
