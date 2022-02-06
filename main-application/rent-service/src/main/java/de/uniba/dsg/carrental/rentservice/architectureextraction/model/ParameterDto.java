package de.uniba.dsg.carrental.rentservice.architectureextraction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto {
    private String name;
    private String location;
    private String description;
    private boolean required;
    private Object defaultValue;
    private SchemaDto schema;
}
