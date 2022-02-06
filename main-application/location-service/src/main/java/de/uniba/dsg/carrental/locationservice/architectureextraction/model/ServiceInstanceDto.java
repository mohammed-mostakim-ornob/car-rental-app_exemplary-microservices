package de.uniba.dsg.carrental.locationservice.architectureextraction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInstanceDto {
    private int port;
    private String basePath;
    private Set<String> protocols;
    private String containerId;
    private String technology;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String host;
    private String region;

    private ServiceDto service;
}
