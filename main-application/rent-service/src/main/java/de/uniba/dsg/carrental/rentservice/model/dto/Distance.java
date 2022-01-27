package de.uniba.dsg.carrental.rentservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Distance {
    private Location from;
    private Location to;
    private Double distance;
}
