package de.uniba.dsg.carrental.locationservice.architectureextraction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int code;
    private String description;
    private SchemaDto schema;
}
