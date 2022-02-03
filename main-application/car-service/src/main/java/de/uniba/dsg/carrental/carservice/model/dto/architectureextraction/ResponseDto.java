package de.uniba.dsg.carrental.carservice.model.dto.architectureextraction;

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
