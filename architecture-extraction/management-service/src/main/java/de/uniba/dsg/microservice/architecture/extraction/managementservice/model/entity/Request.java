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
public class Request {
    @Id
    @GeneratedValue
    private Long id;
    private String from;
    private String to;
    private int count;
    private int average;
    private int max;
    private int min;

    @Relationship(type = "has_method", direction = Relationship.Direction.OUTGOING)
    private Method method;

    @Relationship(type = "has_response", direction = Relationship.Direction.OUTGOING)
    private Response response;

    @Relationship(type = "client", direction = Relationship.Direction.OUTGOING)
    private ServiceInstance client;

    @Relationship(type = "server", direction = Relationship.Direction.OUTGOING)
    private ServiceInstance server;
}
