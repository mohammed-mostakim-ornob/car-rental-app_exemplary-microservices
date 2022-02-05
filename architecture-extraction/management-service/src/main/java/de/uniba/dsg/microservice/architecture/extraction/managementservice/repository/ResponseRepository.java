package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Response;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ResponseRepository extends Neo4jRepository<Response, Long> {
}
