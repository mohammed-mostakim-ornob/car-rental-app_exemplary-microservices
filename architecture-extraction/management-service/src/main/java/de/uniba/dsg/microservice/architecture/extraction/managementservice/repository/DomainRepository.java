package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Domain;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface DomainRepository extends Neo4jRepository<Domain, Long> {
    Optional<Domain> findByName(String name);
}
