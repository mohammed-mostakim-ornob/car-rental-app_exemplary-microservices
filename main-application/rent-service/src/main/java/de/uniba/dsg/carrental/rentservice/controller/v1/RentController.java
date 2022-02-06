package de.uniba.dsg.carrental.rentservice.controller.v1;


import de.uniba.dsg.carrental.rentservice.Constants;
import de.uniba.dsg.carrental.rentservice.exception.BadRequestException;
import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.architectureextraction.helper.ArchitectureExtractionHelper;
import de.uniba.dsg.carrental.rentservice.model.dto.Rent;
import de.uniba.dsg.carrental.rentservice.service.RentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(type = "object"))
                    }),
                    @ApiResponse(responseCode = "400", content = {
                            @Content(schema = @Schema(type = "string"))
                    }),
                    @ApiResponse(responseCode = "500", content = {
                            @Content(schema = @Schema(type = "string"))
                    })
            }
    )
    @CircuitBreaker(name = "circuit-breaker-getRent", fallbackMethod = "getRentFallback")
    public ResponseEntity<?> getRent(@RequestParam String carCode, @RequestParam String from, @RequestParam String to) {
        try {
            validateRentRequestQueryParams(carCode, from, to);

            Rent rent = rentService.calculateRent(carCode, from, to);

            rent.add(linkTo(
                methodOn(RentController.class).getRent(rent.getCarCode(), rent.getFrom(), rent.getTo())
            ).withSelfRel());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_RENT)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_OK)
                    )))
                    .body(rent);
        } catch (BadRequestException | EntityNotFoundException | InvalidRequestParamException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_RENT)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_BAD_REQUEST)
                    )))
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_RENT)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_INTERNAL_SERVER_ERROR)
                    )))
                    .body("Internal Server Error.");
        }
    }

    public ResponseEntity<?> getRentFallback(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(ArchitectureExtractionHelper.setHttpHeaders(Map.ofEntries(
                        Map.entry(Constants.HEADER_METHOD_NAME, ArchitectureExtractionHelper.buildMethodUniqueName(Constants.METHOD_GET_RENT)),
                        Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_INTERNAL_SERVER_ERROR)
                )))
                .body("Rent can not be calculated due to internal server error.");
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
