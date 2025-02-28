package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithId() {
        Car car = new Car();
        car.setCarId("C001");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar);
        assertEquals("C001", createdCar.getCarId());
        assertEquals("Toyota Supra", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(5, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithoutId() {
        Car car = new Car();
        car.setCarName("Honda Civic");
        car.setCarColor("Blue");
        car.setCarQuantity(3);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar);
        assertNotNull(createdCar.getCarId()); // Ensures UUID was generated
        assertEquals("Honda Civic", createdCar.getCarName());
        assertEquals("Blue", createdCar.getCarColor());
        assertEquals(3, createdCar.getCarQuantity());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("C001");
        Car car2 = new Car();
        car2.setCarId("C002");

        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals("C001", iterator.next().getCarId());
        assertTrue(iterator.hasNext());
        assertEquals("C002", iterator.next().getCarId());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("C001");
        carRepository.create(car);

        Car foundCar = carRepository.findById("C001");
        assertNotNull(foundCar);
        assertEquals("C001", foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("C999");
        assertNull(foundCar);
    }

    @Test
    void testUpdateCarSuccess() {
        Car car = new Car();
        car.setCarId("C001");
        car.setCarName("Old Name");
        car.setCarColor("Old Color");
        car.setCarQuantity(2);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("New Color");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("C001", updatedCar);

        assertNotNull(result);
        assertEquals("C001", result.getCarId());
        assertEquals("New Name", result.getCarName());
        assertEquals("New Color", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateCarNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");
        updatedCar.setCarColor("Updated Color");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update("C999", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setCarId("C001");
        carRepository.create(car);

        carRepository.delete("C001");
        assertNull(carRepository.findById("C001"));
    }

    @Test
    void testDeleteCarNotFound() {
        carRepository.delete("C999"); // Should not throw an exception
    }
}
