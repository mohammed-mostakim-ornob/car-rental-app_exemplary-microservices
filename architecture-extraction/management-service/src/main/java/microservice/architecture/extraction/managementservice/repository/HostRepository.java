package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Host;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface HostRepository extends Neo4jRepository<Host, Long> {
    Optional<Host> findByName(String name);
}
