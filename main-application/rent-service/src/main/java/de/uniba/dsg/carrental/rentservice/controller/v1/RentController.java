package de.uniba.dsg.carrental.rentservice.controller.v1;


import de.uniba.dsg.carrental.rentservice.exception.BadRequestException;
import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.model.dto.Rent;
import de.uniba.dsg.carrental.rentservice.service.RentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rent-service/api/v1/rent")
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    @CircuitBreaker(name = "circuit-breaker-calculateRent", fallbackMethod = "calculateRentFallback")
    public ResponseEntity<?> calculateRent(@RequestParam String carCode, @RequestParam String from, @RequestParam String to) {
        try {
            validateRentRequestQueryParams(carCode, from, to);

            Rent rent = rentService.calculateRent(carCode, from, to);

            rent.add(linkTo(
                methodOn(RentController.class).calculateRent(rent.getCarCode(), rent.getFrom(), rent.getTo())
            ).withSelfRel());

            return new ResponseEntity<>(rent, HttpStatus.OK);
        } catch (BadRequestException | EntityNotFoundException | InvalidRequestParamException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> calculateRentFallback(Exception ex) {
        return new ResponseEntity<>("Rent can not be calculated due to internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void validateRentRequestQueryParams(String carCode, String from, String to) throws BadRequestException {
        List<String> errors = new ArrayList<>();

        if (carCode.isEmpty())
            errors.add("Invalid Car Code");

        if (from.isEmpty())
            errors.add("Invalid From location");

        if (to.isEmpty())
            errors.add("Invalid To location");

        if (!errors.isEmpty())
            throw new BadRequestException(String.join(", ", errors));
    }
}
