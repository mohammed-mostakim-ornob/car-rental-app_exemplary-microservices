package de.uniba.dsg.carrental.rentservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent extends RepresentationModel<Rent> {
    private String carCode;
    private String from;
    private String to;
    private Double rent;
}
