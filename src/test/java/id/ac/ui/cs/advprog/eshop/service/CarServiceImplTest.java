package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        car = new Car();
        car.setCarId("C001");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals(car, createdCar);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = Arrays.asList(car);
        Iterator<Car> carIterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(carRepository.findAll()).thenReturn(List.<Car>of().iterator());

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("C001")).thenReturn(car);

        Car foundCar = carService.findById("C001");

        assertNotNull(foundCar);
        assertEquals(car, foundCar);
        verify(carRepository, times(1)).findById("C001");
    }

    @Test
    void testFindByIdNotFound() {
        when(carRepository.findById("C999")).thenReturn(null);

        Car foundCar = carService.findById("C999");

        assertNull(foundCar);
        verify(carRepository, times(1)).findById("C999");
    }

    @Test
    void testUpdate() {
        carService.update("C001", car);

        verify(carRepository, times(1)).update("C001", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("C001");

        verify(carRepository, times(1)).delete("C001");
    }
}
