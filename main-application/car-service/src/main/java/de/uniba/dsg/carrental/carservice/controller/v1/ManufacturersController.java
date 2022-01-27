package de.uniba.dsg.carrental.carservice.controller.v1;

import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.model.data.Car;
import de.uniba.dsg.carrental.carservice.model.data.Manufacturer;
import de.uniba.dsg.carrental.carservice.service.CarService;
import de.uniba.dsg.carrental.carservice.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ManufacturersController {
    private final CarService carService;
    private final ManufactureService manufactureService;

    @Autowired
    public ManufacturersController(CarService carService, ManufactureService manufactureService) {
        this.carService = carService;
        this.manufactureService = manufactureService;
    }

    @GetMapping("/api/v1/manufacturers")
    public ResponseEntity<?> getManufacturers() {
        try {
            List<Manufacturer> manufacturers = manufactureService.getAllManufacturers();

            manufacturers.forEach(manufacturer -> manufacturer.add(linkTo(
                    methodOn(ManufacturersController.class).getManufacturerCars(manufacturer.getName())
            ).withSelfRel()));

            Link link = linkTo(
                    methodOn(ManufacturersController.class).getManufacturers()
            ).withRel("getCars");

            return new ResponseEntity<>(CollectionModel.of(manufacturers, link), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/manufacturers/{name}/cars")
    public ResponseEntity<?> getManufacturerCars(@PathVariable String name) {
        try {
            Manufacturer manufacturer = manufactureService.getManufacturer(name);

            List<Car> cars = carService.getCarsByManufacturer(manufacturer.getName());

            cars.forEach(car -> {
                car.add(linkTo(
                    methodOn(CarsController.class).getCar(car.getCode())
                ).withSelfRel());

                car.getManufacturer().add(linkTo(
                    methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel("getCars")
                );
            });

            Link link = linkTo(
                    methodOn(ManufacturersController.class).getManufacturerCars(name)
            ).withSelfRel();

            return new ResponseEntity<>(CollectionModel.of(cars, link), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
