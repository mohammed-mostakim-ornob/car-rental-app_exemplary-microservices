package de.uniba.dsg.microservice.architecture.extraction.aggregationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInstanceDto {
    private String requestLogEndpoint;
}
