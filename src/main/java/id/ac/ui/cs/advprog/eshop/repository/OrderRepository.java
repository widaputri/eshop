package id.ac.ui.cs.advprog.eshop.repository;


import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderRepository implements CRUDRepository<Order>{
    private List<Order> orderData = new ArrayList<>();

    @Override
    public Order create(Order entity) {
        return null;
    }

    @Override
    public Iterator<Order> findAll() {
        return null;
    }

    @Override
    public Order findById(String id) {
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        return null;
    }

    @Override
    public Order update(String id, Order entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
