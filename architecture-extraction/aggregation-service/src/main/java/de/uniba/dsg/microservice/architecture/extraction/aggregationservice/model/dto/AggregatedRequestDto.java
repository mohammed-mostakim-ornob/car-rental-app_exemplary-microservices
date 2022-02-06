package de.uniba.dsg.microservice.architecture.extraction.aggregationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedRequestDto {
    private String clientId;
    private String serverId;
    private String methodId;
    private String responseCode;
    private Long count;
    private Double average;
    private Long minimum;
    private Long maximum;
}
