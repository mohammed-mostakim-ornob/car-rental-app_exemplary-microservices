package de.uniba.dsg.microservice.architecture.extraction.managementservice.controller;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto.ServiceInstanceDto;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.ServiceInstance;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management-service/ServiceInstances")
public class ServiceInstanceController {

    private final InstanceService instanceService;

    @Autowired
    public ServiceInstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping
    public ResponseEntity<?> getServiceInstances() {
        try {
            List<ServiceInstance> instances = instanceService.getAllInstances();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(instances);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error.");
        }
    }

    @PostMapping
    public ResponseEntity<?> addServiceInstance(@RequestBody ServiceInstanceDto instance) {
        try {
            instanceService.addInstance(instance);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Instance successfully added.");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error.");
        }
    }
}
