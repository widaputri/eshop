package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final String method;
    @Setter
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment(status);
    }

    private String validatePayment(String initialStatus) {
        if ("Voucher".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (!isValidVoucherCode(voucherCode)) {
                return "REJECTED";
            }
            return "SUCCESS";
        }
        if ("Bank Transfer".equals(method)) {
            if (isInvalidBankTransfer(paymentData)) {
                return "REJECTED";
            }
        }
        return initialStatus;
    }

    private boolean isValidVoucherCode(String code) {
        return code != null && code.length() == 16 && code.startsWith("ESHOP")
                && code.replaceAll("[^0-9]", "").length() == 8;
    }

    private boolean isInvalidBankTransfer(Map<String, String> paymentData) {
        return !paymentData.containsKey("bankName") || paymentData.get("bankName") == null || paymentData.get("bankName").isEmpty()
                || !paymentData.containsKey("referenceCode") || paymentData.get("referenceCode") == null || paymentData.get("referenceCode").isEmpty();
    }
}
