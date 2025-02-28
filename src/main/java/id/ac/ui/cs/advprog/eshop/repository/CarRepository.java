package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CarRepository {

    private List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Car findById(String id) {
        return carData.stream().filter(car -> car.getCarId().equals(id)).findFirst().orElse(null);
    }

    public void update(String id, Car updatedCar) {
        carData.replaceAll(car -> car.getCarId().equals(id) ? updatedCar : car);
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}
