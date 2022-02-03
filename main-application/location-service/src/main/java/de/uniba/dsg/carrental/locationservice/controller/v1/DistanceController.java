package de.uniba.dsg.carrental.locationservice.controller.v1;

import de.uniba.dsg.carrental.locationservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.model.data.Distance;
import de.uniba.dsg.carrental.locationservice.service.DistanceService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/location-service/api/v1/distance")
public class DistanceController {
    private final DistanceService distanceService;

    @Autowired
    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(type = "object"))
                    }),
                    @ApiResponse(responseCode = "400", content = {
                            @Content(schema = @Schema(type = "string"))
                    }),
                    @ApiResponse(responseCode = "404", content = {
                            @Content(schema = @Schema(type = "string"))
                    }),
                    @ApiResponse(responseCode = "500", content = {
                            @Content(schema = @Schema(type = "string"))
                    })
            }
    )
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
