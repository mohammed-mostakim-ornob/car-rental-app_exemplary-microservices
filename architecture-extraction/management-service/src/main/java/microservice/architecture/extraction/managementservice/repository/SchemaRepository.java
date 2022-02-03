package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Schema;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SchemaRepository extends Neo4jRepository<Schema, Long> {
}
