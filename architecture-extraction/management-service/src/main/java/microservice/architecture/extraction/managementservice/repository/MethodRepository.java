package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Method;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MethodRepository extends Neo4jRepository<Method, Long> {
}
