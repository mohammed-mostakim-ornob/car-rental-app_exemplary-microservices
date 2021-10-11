package de.uniba.dsg.carrental.locationservice.controller.v1;

import de.uniba.dsg.carrental.locationservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.model.data.Distance;
import de.uniba.dsg.carrental.locationservice.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DistanceController {
    private final DistanceService distanceService;

    @Autowired
    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("/api/v1/distance")
    public ResponseEntity<?> getDistance(@RequestParam String from, @RequestParam String to) {
        try {
            validateDistanceRequestParam(from, to);

            Distance distance = distanceService.getLocationByFromAndToCode(from, to);

            distance.add(linkTo(
                methodOn(DistanceController.class).getDistance(distance.getFrom().getCode(), distance.getTo().getCode())
            ).withSelfRel());

            distance.getFrom().add(linkTo(
               methodOn(LocationsController.class).getLocation(distance.getFrom().getCode())).withSelfRel()
            );

            distance.getTo().add(linkTo(
                    methodOn(LocationsController.class).getLocation(distance.getTo().getCode())).withSelfRel()
            );

            return new  ResponseEntity<>(distance, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidRequestParamException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateDistanceRequestParam(String from, String to) throws InvalidRequestParamException {
        if (from == null || from.isEmpty() || from.isBlank())
            throw new InvalidRequestParamException("From Location is required.");

        if (to == null || to.isEmpty() || to.isBlank())
            throw new InvalidRequestParamException("To Location is required.");
    }
}
