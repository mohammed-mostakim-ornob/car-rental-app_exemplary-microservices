package de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity;

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
public class Response {
    @Id
    @GeneratedValue
    private Long id;
    private int code;
    private String description;

    @Relationship(type = "has_schema", direction = Relationship.Direction.OUTGOING)
    private Schema schema;
}
