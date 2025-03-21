package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final Map<String, String> paymentOrderMapping = new HashMap<>(); // PaymentID -> OrderID mapping

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = validatePayment(method, paymentData);
        Payment payment = new Payment(UUID.randomUUID().toString(), method, status, paymentData);
        paymentRepository.create(payment);

        paymentOrderMapping.put(payment.getId(), order.getId()); // Link payment to order
        orderRepository.save(order);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!List.of("SUCCESS", "REJECTED", "PENDING").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        payment.setStatus(status);
        paymentRepository.update(payment.getId(), payment);

        // Update related order status
        String orderId = getOrderIdForPayment(payment.getId());
        if (orderId != null) {
            Order order = orderRepository.findById(orderId);
            if (order != null) {
                if ("SUCCESS".equals(status)) {
                    order.setStatus("SUCCESS");
                } else if ("REJECTED".equals(status)) {
                    order.setStatus("FAILED");
                }
                orderRepository.save(order);
            }
        }
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentRepository.findAll().forEachRemaining(paymentList::add);
        return paymentList;
    }

    private String validatePayment(String method, Map<String, String> paymentData) {
        if ("Voucher".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            return isValidVoucherCode(voucherCode) ? "SUCCESS" : "REJECTED";
        } else if ("Bank Transfer".equals(method)) {
            return isInvalidBankTransfer(paymentData) ? "REJECTED" : "PENDING";
        }
        return "PENDING";
    }

    private boolean isValidVoucherCode(String code) {
        if (code == null || code.length() != 16 || !code.startsWith("ESHOP")) {
            return false;
        }
        int digitCount = code.replaceAll("[^0-9]", "").length();
        return digitCount == 8;
    }

    private boolean isInvalidBankTransfer(Map<String, String> paymentData) {
        return paymentData == null
                || paymentData.get("bankName") == null
                || paymentData.get("bankName").isEmpty()
                || paymentData.get("referenceCode") == null
                || paymentData.get("referenceCode").isEmpty();
    }

    protected String getOrderIdForPayment(String paymentId) {
        return paymentOrderMapping.get(paymentId);
    }
}
