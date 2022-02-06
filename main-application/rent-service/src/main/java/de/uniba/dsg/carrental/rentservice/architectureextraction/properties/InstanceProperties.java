package de.uniba.dsg.carrental.rentservice.architectureextraction.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "instance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstanceProperties {
    int port;
    private String host;
    private String region;
    private String basePath;
    private String containerId;
    private String technology;
    private String requestLogEndpoint;
    private String[] protocols;
}
