package microservice.architecture.extraction.managementservice.repository;

import microservice.architecture.extraction.managementservice.model.entity.Contact;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ContactRepository extends Neo4jRepository<Contact, Long> {
    Optional<Contact> findByName(String name);
}
