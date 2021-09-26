package de.uniba.dsg.carrental.locationservice.repository;

import de.uniba.dsg.carrental.locationservice.model.data.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistanceRepository extends JpaRepository<Distance, Long> {
    Optional<Distance> findByFromCodeAndToCode(String fromCode, String toCode);
}
