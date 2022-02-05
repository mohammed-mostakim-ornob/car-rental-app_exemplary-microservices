package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Request;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RequestRepository extends Neo4jRepository<Request, Long> {
}
