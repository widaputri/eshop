package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.*;

public class PaymentRepository implements CRUDRepository<Payment> {
    private final Map<String, Payment> paymentStorage = new HashMap<>();

    @Override
    public Payment create(Payment entity) {
        // TODO: Implement create logic
        return null;
    }

    @Override
    public Iterator<Payment> findAll() {
        // TODO: Implement findAll logic
        return null;
    }

    @Override
    public Payment findById(String id) {
        // TODO: Implement findById logic
        return null;
    }

    @Override
    public Payment update(String id, Payment entity) {
        // TODO: Implement update logic
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO: Implement delete logic
    }
}
