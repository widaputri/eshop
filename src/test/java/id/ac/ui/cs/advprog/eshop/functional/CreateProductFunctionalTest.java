package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_success(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        assertEquals("Create New Product", driver.getTitle());

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("10");
        submitButton.click();

        driver.get(baseUrl + "/product/list");
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("Test Product"));
        assertTrue(pageSource.contains("10"));
    }

    @Test
    void createProduct_emptyName(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        assertEquals("Create New Product", driver.getTitle());

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("");
        quantityInput.sendKeys("5");
        submitButton.click();

        assertEquals("Create New Product", driver.getTitle());

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Name is required"));
    }

    @Test
    void createProduct_negativeQuantity(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        assertEquals("Create New Product", driver.getTitle());

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("Invalid Product");
        quantityInput.sendKeys("-5");
        submitButton.click();

        assertEquals("Create New Product", driver.getTitle());

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Quantity must be positive"));
    }
}
