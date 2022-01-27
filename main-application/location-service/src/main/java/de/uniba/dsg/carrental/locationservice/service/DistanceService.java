package de.uniba.dsg.carrental.locationservice.service;

import de.uniba.dsg.carrental.locationservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.locationservice.model.data.Distance;
import de.uniba.dsg.carrental.locationservice.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistanceService {
    private final DistanceRepository distanceRepository;

    @Autowired
    public DistanceService(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    public Distance getLocationByFromAndToCode(String from, String to) throws EntityNotFoundException {
        Optional<Distance> optionalDistance = distanceRepository.findByFromCodeAndToCode(from, to);

        if (optionalDistance.isEmpty())
            throw new EntityNotFoundException("No distance found for locations from: " + from + " and to: " + to);

        return optionalDistance.get();
    }
}
