package de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaDto {
    private String name;
    private String type;
}
