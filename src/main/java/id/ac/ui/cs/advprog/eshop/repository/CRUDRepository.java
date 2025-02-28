package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface CRUDRepository<T> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(String id);
    T update(String id, T entity);
    void delete(String id);
}
