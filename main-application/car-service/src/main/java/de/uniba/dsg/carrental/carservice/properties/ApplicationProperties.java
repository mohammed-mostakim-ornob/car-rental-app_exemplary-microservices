package de.uniba.dsg.carrental.carservice.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProperties {
    private String title;
    private String version;
    private String description;
    private String domain;
    private String technologyStack;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    private Map<String, String> contactPerson;
}
