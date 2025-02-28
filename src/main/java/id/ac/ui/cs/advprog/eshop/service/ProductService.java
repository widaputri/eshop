package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public Product findById(String id);
    public void delete(Product product);
    public void delete(String id);
    public Product edit(Product product);
    public Product edit(String id, Product product);
}
