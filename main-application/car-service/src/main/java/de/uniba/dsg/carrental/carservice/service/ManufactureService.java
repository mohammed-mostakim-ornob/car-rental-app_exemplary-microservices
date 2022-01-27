package de.uniba.dsg.carrental.carservice.service;

import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.model.data.Manufacturer;
import de.uniba.dsg.carrental.carservice.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufactureService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufactureService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturer(String name) throws EntityNotFoundException {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(name);

        if (optionalManufacturer.isEmpty())
            throw new EntityNotFoundException("No Manufacturer found with Name: " + name);

        return optionalManufacturer.get();
    }
}
