package de.uniba.dsg.carrental.carservice.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends RepresentationModel<Car> {
    @Id
    @NotNull
    private String code;

    @ManyToOne
    @NotNull
    private Manufacturer manufacturer;

    @NotNull
    private String model;

    @NotNull
    private Double rentPerKilo;
}
