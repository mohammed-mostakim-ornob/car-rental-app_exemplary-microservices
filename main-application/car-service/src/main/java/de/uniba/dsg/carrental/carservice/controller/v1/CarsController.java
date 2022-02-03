package de.uniba.dsg.carrental.carservice.controller.v1;

import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.model.data.Car;
import de.uniba.dsg.carrental.carservice.service.CarService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/car-service/api/v1/cars")
public class CarsController {
    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(type = "object"))
                    }),
                    @ApiResponse(responseCode = "500", content = {
                            @Content(schema = @Schema(type = "string"))
                    })
            }
    )
    public ResponseEntity<?> getCars() {
        try {
            List<Car> cars = carService.getAllCars();

            cars.forEach(car -> {
                car.add(linkTo(
                        methodOn(CarsController.class).getCar(car.getCode())
                ).withSelfRel());

                car.getManufacturer().add(linkTo(
                        methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel("getCars")
                );
            });

            Link link = linkTo(
                    methodOn(CarsController.class).getCars()
            ).withSelfRel();

            return new ResponseEntity<>(CollectionModel.of(cars, link), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{code}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(type = "object"))
                    }),
                    @ApiResponse(responseCode = "404", content = {
                            @Content(schema = @Schema(type = "string"))
                    }),
                    @ApiResponse(responseCode = "500", content = {
                            @Content(schema = @Schema(type = "string"))
                    })
            }
    )
    public ResponseEntity<?> getCar(@PathVariable String code) {
        try {
            Car car = carService.getCar(code);

            car.add(linkTo(
                methodOn(CarsController.class).getCar(car.getCode())
            ).withSelfRel());

            car.getManufacturer().add(linkTo(
                methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel("getCars")
            );

            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
