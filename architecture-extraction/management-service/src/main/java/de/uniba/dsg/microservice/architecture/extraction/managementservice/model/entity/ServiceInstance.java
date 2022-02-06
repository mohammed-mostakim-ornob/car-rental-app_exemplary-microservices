package de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInstance {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private int port;
    private String basePath;
    private String containerId;
    private String technology;
    private String requestLogEndpoint;
    private Set<String> protocols;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @JsonIgnore
    @Relationship(type = "is_running_on", direction = Relationship.Direction.OUTGOING)
    private Host host;

    @JsonIgnore
    @Relationship(type = "has_instance", direction = Relationship.Direction.INCOMING)
    private Service service;
}
