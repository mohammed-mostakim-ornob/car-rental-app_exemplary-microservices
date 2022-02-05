package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Parameter;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ParameterRepository extends Neo4jRepository<Parameter, Long> {
}
