package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String id) {
        Iterator<Product> productIterator = productRepository.findAll();
        Product product = null;
        while (productIterator.hasNext()) {
            product = productIterator.next();
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void delete(String id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Override
    public Product edit(Product product) {
        return productRepository.update(product);
    }

    @Override
    public Product edit(String id, Product product) {
        product.setProductId(id);
        return productRepository.update(product);
    }
}
