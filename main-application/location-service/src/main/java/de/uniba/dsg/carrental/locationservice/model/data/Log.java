package de.uniba.dsg.carrental.locationservice.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    String serverId;
    String clientId;
    String methodId;
    String responseCode;
    Long invocationTime;
    Long responseTime;
}
