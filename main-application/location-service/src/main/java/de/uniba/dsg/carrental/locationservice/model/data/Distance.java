package de.uniba.dsg.carrental.locationservice.model.data;

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
public class Distance extends RepresentationModel<Distance> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_location")
    @NotNull
    private Location from;

    @ManyToOne
    @JoinColumn(name = "to_location")
    @NotNull
    private Location to;

    @NotNull
    private Double distance;
}
