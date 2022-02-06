package de.uniba.dsg.microservice.architecture.extraction.managementservice.repository;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Method;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface MethodRepository extends Neo4jRepository<Method, Long> {
    @Query("""
            MATCH (m:Method)-[hs:has_response]->(r:Response)
            WHERE m.description = $description
            RETURN m, collect(r), collect(hs)
           """)
    Optional<Method> findMethodByDescription(String description);
}
