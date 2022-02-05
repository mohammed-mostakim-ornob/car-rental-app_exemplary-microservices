package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Region;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface RegionRepository extends Neo4jRepository<Region, Long> {
    Optional<Region> findByName(String name);
}
