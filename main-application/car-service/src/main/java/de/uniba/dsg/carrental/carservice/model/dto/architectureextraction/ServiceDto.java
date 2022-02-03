package de.uniba.dsg.carrental.carservice.model.dto.architectureextraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private String title;
    private String version;
    private String description;
    private String technologyStack;
    private LocalDate creationDate;

    private String domainName;
    private String contactName;
    private String contactEmail;

    private Set<MethodDto> methods;
}
