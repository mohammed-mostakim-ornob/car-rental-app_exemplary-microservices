package de.uniba.dsg.carrental.rentservice.model.dto.architectureextraction;

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