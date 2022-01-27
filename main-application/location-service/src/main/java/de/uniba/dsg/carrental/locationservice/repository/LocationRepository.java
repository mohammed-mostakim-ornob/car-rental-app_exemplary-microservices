package de.uniba.dsg.carrental.locationservice.repository;

import de.uniba.dsg.carrental.locationservice.model.data.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByCode(String code);
}
