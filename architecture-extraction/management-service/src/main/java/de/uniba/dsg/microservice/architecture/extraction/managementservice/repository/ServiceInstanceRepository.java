package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.ServiceInstance;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ServiceInstanceRepository extends Neo4jRepository<ServiceInstance, Long> {
}
