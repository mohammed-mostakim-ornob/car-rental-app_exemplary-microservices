package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Request;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RequestRepository extends Neo4jRepository<Request, Long> {
}
