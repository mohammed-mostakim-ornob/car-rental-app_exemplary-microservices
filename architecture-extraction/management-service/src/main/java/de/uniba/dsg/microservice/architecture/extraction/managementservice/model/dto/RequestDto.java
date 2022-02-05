package de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private int clientPort;
    private int serverPort;
    private String clientHost;
    private String serverHost;

    private int count;
    private int avg;
    private int max;
    private int min;
}
