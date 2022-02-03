package de.uniba.dsg.carrental.locationservice.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "architecture-extraction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchitectureExtractionProperties {
    private String managementServiceBaseUrl;
    private String createInstanceEndpoint;
}
