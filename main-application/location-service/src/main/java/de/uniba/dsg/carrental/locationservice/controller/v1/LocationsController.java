package de.uniba.dsg.carrental.locationservice.controller.v1;

import de.uniba.dsg.carrental.locationservice.Constants;
import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.helper.Helper;
import de.uniba.dsg.carrental.locationservice.model.data.Location;
import de.uniba.dsg.carrental.locationservice.service.LocationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/location-service/api/v1/locations")
public class LocationsController {
    private final LocationService locationService;

    @Autowired
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
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
    public ResponseEntity<?> getLocations() {
        try {
            List<Location> locations = locationService.getAllLocations();

            locations.forEach(location -> location.add(linkTo(
                    methodOn(LocationsController.class).getLocation(location.getCode())
            ).withSelfRel()));

            Link link = linkTo(
                    methodOn(LocationsController.class).getLocations()
            ).withSelfRel();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(Helper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, Helper.buildMethodUniqueName(Constants.METHOD_GET_LOCATIONS)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_OK)
                    )))
                    .body(CollectionModel.of(locations, link));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(Helper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, Helper.buildMethodUniqueName(Constants.METHOD_GET_LOCATIONS)),
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
    public ResponseEntity<?> getLocation(@PathVariable String code) {
        try {
            Location location = locationService.getLocationByCode(code);

            location.add(linkTo(
                methodOn(LocationsController.class).getLocation(location.getCode())
            ).withSelfRel());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(Helper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, Helper.buildMethodUniqueName(Constants.METHOD_GET_LOCATION)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_OK)
                    )))
                    .body(location);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .headers(Helper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, Helper.buildMethodUniqueName(Constants.METHOD_GET_LOCATION)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_NOT_FOUND)
                    )))
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(Helper.setHttpHeaders(Map.ofEntries(
                            Map.entry(Constants.HEADER_METHOD_NAME, Helper.buildMethodUniqueName(Constants.METHOD_GET_LOCATION)),
                            Map.entry(Constants.HEADER_RESPONSE_CODE, Constants.RESPONSE_STATUS_INTERNAL_SERVER_ERROR)
                    )))
                    .body("Internal Server Error.");
        }
    }
}
