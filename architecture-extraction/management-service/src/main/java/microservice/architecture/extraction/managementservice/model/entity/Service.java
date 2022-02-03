package microservice.architecture.extraction.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String version;
    private String description;
    private String technologyStack;
    private LocalDate creationDate;

    @Relationship(type = "has_contact", direction = Relationship.Direction.OUTGOING)
    private Contact contact;

    @Relationship(type = "has_domain", direction = Relationship.Direction.OUTGOING)
    private Domain domain;

    @Relationship(type = "has_method", direction = Relationship.Direction.OUTGOING)
    private Set<Method> methods;

    //@Relationship(type = "has_instance", direction = Relationship.Direction.OUTGOING)
    //private Set<ServiceInstance> serviceInstances;
}
