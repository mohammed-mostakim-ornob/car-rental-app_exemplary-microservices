package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ServiceRepository extends Neo4jRepository<Service, Long> {
    Optional<Service> findByTitleAndVersion(String title, String version);
}
