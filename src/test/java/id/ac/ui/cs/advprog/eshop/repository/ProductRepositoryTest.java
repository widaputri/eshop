package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productRepository.create(product);

        assertNotNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));

        productRepository.delete(product);
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Old Name");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdateProductFalseHit() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Original Name");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-matching-id"); // This ID does not match any existing product
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);

        assertNull(result, "Update should return null when no matching product ID is found");
    }

    @Test
    void testFindByIdFalseHit() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Original Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product result = productRepository.findById("non-existing-id");

        assertNull(result, "findById() should return null when no matching product ID is found");
    }
}
