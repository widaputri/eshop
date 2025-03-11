package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "CreateCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "CarList";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("car", carService.findById(id));
        return "EditCar";
    }

    @PostMapping("/edit/{id}")
    public String editCarPost(@PathVariable("id") String carId, @ModelAttribute Car car) {
        carService.update(carId, car);
        return "redirect:/car/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") String carId) {
        carService.deleteCarById(carId);
        return "redirect:/car/list";
    }
}
