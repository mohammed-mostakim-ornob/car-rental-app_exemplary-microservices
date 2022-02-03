package de.uniba.dsg.carrental.rentservice.model.dto.architectureextraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodDto {
    private String path;
    private String method;
    private String Summary;
    private String description;
    private Set<ParameterDto> parameters;
    private Set<ResponseDto> responses;
}
