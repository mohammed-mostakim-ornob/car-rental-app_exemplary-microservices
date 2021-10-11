package de.uniba.dsg.carrental.locationservice.controller.v1;

import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.model.data.Location;
import de.uniba.dsg.carrental.locationservice.service.LocationService;
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
public class LocationsController {
    private final LocationService locationService;

    @Autowired
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/api/v1/locations")
    public ResponseEntity<?> getLocations() {
        try {
            List<Location> locations = locationService.getAllLocations();

            locations.forEach(location -> location.add(linkTo(
                    methodOn(LocationsController.class).getLocation(location.getCode())
            ).withSelfRel()));

            Link link = linkTo(
                    methodOn(LocationsController.class).getLocations()
            ).withSelfRel();

            return new ResponseEntity<>(CollectionModel.of(locations, link), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/locations/{code}")
    public ResponseEntity<?> getLocation(@PathVariable String code) {
        try {
            Location location = locationService.getLocationByCode(code);

            location.add(linkTo(
                methodOn(LocationsController.class).getLocation(location.getCode())
            ).withSelfRel());

            return new  ResponseEntity<>(location, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
