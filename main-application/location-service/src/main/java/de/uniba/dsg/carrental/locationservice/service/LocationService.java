package de.uniba.dsg.carrental.locationservice.service;

import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.model.data.Location;
import de.uniba.dsg.carrental.locationservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationByCode(String code) throws EntityNotFoundException {
        Optional<Location> optionalLocation = locationRepository.findByCode(code);

        if (optionalLocation.isEmpty())
            throw new EntityNotFoundException("Location not found with Code: " + code);

        return optionalLocation.get();
    }
}
