package id.ac.ui.cs.advprog.eshop.repository;


import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class OrderRepository{
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order entity) {
        int i = 0;
        for (Order order : orderData) {
            if (order.getId().equals(entity.getId())) {
                orderData.remove(i);
                orderData.add(i, entity);
                return entity;
            }
            i += 1;
        }

        orderData.add(entity);
        return entity;
    }

    public Iterator<Order> findAll() {
        return null;
    }

    public Order findById(String id) {
        for (Order savedOrder : orderData) {
            if (savedOrder.getId().equals(id)) {
                return savedOrder;
            }
        }
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderData) {
            if (order.getAuthor().equals(author)) {
                result.add(order);
            }
        }
        return result;
    }
}
