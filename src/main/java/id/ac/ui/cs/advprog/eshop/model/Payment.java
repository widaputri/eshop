package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (method == null || paymentData == null) {
            throw new IllegalArgumentException("Method and paymentData cannot be null");
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = status;
    }
}
