package de.uniba.dsg.carrental.rentservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String code;
    private String model;
    private Double rentPerKilo;
}
