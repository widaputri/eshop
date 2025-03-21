package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepository implements CRUDRepository<Payment> {
    private final Map<String, Payment> paymentStorage = new HashMap<>();

    @Override
    public Payment create(Payment entity) {
        paymentStorage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Iterator<Payment> findAll() {
        return paymentStorage.values().iterator();
    }

    @Override
    public Payment findById(String id) {
        return paymentStorage.get(id);
    }

    @Override
    public Payment update(String id, Payment entity) {
        if (paymentStorage.containsKey(id)) {
            paymentStorage.put(id, entity);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        paymentStorage.remove(id);
    }
}
