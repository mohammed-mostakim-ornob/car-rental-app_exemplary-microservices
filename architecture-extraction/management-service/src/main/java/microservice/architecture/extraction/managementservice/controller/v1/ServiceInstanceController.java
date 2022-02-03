package microservice.architecture.extraction.managementservice.controller.v1;

import microservice.architecture.extraction.managementservice.model.dto.ServiceInstanceDto;
import microservice.architecture.extraction.managementservice.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management-service/ServiceInstance")
public class ServiceInstanceController {

    private final InstanceService instanceService;

    @Autowired
    public ServiceInstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping
    public ResponseEntity<?> addServiceInstance(@RequestBody ServiceInstanceDto instance) {
        instanceService.addInstance(instance);

        return new ResponseEntity<>("Instance successfully added.", HttpStatus.CREATED);
    }
}
