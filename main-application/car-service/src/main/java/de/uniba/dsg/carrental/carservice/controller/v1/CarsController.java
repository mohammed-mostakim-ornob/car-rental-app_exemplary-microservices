package de.uniba.dsg.carrental.carservice.controller.v1;

import de.uniba.dsg.carrental.carservice.Constants;
import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.architectureextraction.helper.ArchitectureExtractionHelper;
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
import java.util.Map;

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
                        methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel(Constants.METHOD_GET_CARS)
                );
            });

            Link link = linkTo(
                    methodOn(CarsController.class).getCars()
            ).withSelfRel();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_CARS)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_OK)
                    )))
                    .body(CollectionModel.of(cars, link));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_CARS)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_INTERNAL_SERVER_ERROR)
                    )))
                    .body("Internal Server Error.");
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
                methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel(Constants.METHOD_GET_CARS)
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_CAR)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_OK)
                    )))
                    .body(car);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_CAR)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_NOT_FOUND)
                    )))
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_CAR)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_INTERNAL_SERVER_ERROR)
                    )))
                    .body("Internal Server Error.");
        }
    }
}
