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
    private Long count;
    private Double average;
    private Long max;
    private Long min;

    @Relationship(type = "client", direction = Relationship.Direction.OUTGOING)
    private ServiceInstance from;

    @Relationship(type = "server", direction = Relationship.Direction.OUTGOING)
    private ServiceInstance to;

    @Relationship(type = "client_method", direction = Relationship.Direction.OUTGOING)
    private Method method;

    @Relationship(type = "server_response", direction = Relationship.Direction.OUTGOING)
    private Response response;
}
