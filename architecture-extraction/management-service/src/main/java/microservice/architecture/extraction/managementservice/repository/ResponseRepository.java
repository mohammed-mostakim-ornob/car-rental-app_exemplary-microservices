package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Response;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ResponseRepository extends Neo4jRepository<Response, Long> {
}
