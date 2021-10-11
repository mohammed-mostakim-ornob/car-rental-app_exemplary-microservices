package de.uniba.dsg.carrental.carservice.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends RepresentationModel<Car> {
    @Id
    private String code;

    @ManyToOne
    private Manufacturer manufacturer;

    private String model;

    private Double rentPerKilo;
}
