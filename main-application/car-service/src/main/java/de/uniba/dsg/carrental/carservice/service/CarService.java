package de.uniba.dsg.carrental.carservice.service;

import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.model.data.Car;
import de.uniba.dsg.carrental.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String code) throws EntityNotFoundException {
        Optional<Car> optionalCar = carRepository.findById(code);

        if (optionalCar.isEmpty())
            throw new EntityNotFoundException("No Car found with Code: " + code);

        return optionalCar.get();
    }

    public List<Car> getCarsByManufacturer(String manufacturerName) {
        return carRepository.findCarsByManufacturerName(manufacturerName);
    }
}
