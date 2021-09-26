package de.uniba.dsg.carrental.carservice.repository;

import de.uniba.dsg.carrental.carservice.model.data.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findCarsByManufacturerName(String manufacturerName);
}
