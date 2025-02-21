package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ProductExists() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products.iterator());

        Product foundProduct = productService.findById("1");

        assertNotNull(foundProduct);
        assertEquals("1", foundProduct.getProductId());
        assertEquals("Test Product", foundProduct.getProductName());
    }

    @Test
    void findById_ProductDoesNotExist() {
        List<Product> products = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(products.iterator());

        Product foundProduct = productService.findById("999");

        assertNull(foundProduct);
    }

    @Test
    void findAll_ReturnsList() {
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productRepository.findAll()).thenReturn(products.iterator());

        List<Product> foundProducts = productService.findAll();

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
        assertEquals("Product 1", foundProducts.get(0).getProductName());
        assertEquals("Product 2", foundProducts.get(1).getProductName());
    }

    @Test
    void editProduct_success() {
        Product existingProduct = new Product();
        existingProduct.setProductId("1");
        existingProduct.setProductName("You are my fire");

        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("The one desire");

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.edit(updatedProduct);

        assertNotNull(result);
        assertEquals("The one desire", result.getProductName());
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void editProduct_nonExistent() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("999");
        nonExistentProduct.setProductName("Believe when I say");

        when(productRepository.update(any(Product.class))).thenReturn(null);

        Product result = productService.edit(nonExistentProduct);

        assertNull(result);
        verify(productRepository, times(1)).update(nonExistentProduct);
    }

    @Test
    void deleteProduct_success() {
        Product productToDelete = new Product();
        productToDelete.setProductId("1");
        productToDelete.setProductName("I want it that way");

        doNothing().when(productRepository).delete(productToDelete);

        productService.delete(productToDelete);

        verify(productRepository, times(1)).delete(productToDelete);
    }

    @Test
    void deleteProduct_nonExistent() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("999");
        nonExistentProduct.setProductName("Tell me why");

        doThrow(new RuntimeException("Product not found")).when(productRepository).delete(nonExistentProduct);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.delete(nonExistentProduct);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).delete(nonExistentProduct);
    }
}
