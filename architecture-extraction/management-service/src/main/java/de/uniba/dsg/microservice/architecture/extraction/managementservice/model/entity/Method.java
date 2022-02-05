package de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Method {
    @Id
    @GeneratedValue
    private Long id;
    private String path;
    private String method;
    private String summary;
    private String description;

    @Relationship(type = "has_parameter", direction = Relationship.Direction.OUTGOING)
    private Set<Parameter> parameters;

    @Relationship(type = "has_response", direction = Relationship.Direction.OUTGOING)
    private Set<Response> responses;
}
