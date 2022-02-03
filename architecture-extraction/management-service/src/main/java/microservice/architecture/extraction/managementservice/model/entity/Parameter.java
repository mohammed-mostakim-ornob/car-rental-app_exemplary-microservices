package microservice.architecture.extraction.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    @Id
    @GeneratedValue
    private Long id;
    private String location;
    private String name;
    private String description;
    private Object defaultValue;
    private boolean isRequired;

    @Relationship(type = "has_schema", direction = Relationship.Direction.OUTGOING)
    private Schema schema;
}
