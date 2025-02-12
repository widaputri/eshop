package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void delete(Product product) {
        productData.remove(product);
    }

    public Product update(Product newProduct) {
        for (Product product : productData) {
            if (product.getProductId().equals(newProduct.getProductId())) {
                product.setProductName(newProduct.getProductName());
                product.setProductQuantity(newProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}