package de.uniba.dsg.carrental.locationservice.architectureextraction.service;

import de.uniba.dsg.carrental.locationservice.architectureextraction.model.ServiceDto;
import de.uniba.dsg.carrental.locationservice.architectureextraction.model.ServiceInstanceDto;
import de.uniba.dsg.carrental.locationservice.architectureextraction.properties.ApplicationProperties;
import de.uniba.dsg.carrental.locationservice.architectureextraction.properties.InstanceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ArchitectureExtractionService {
    private final ApiDocService apiDocService;
    private final InstanceProperties instanceProperties;
    private final ApplicationProperties applicationProperties;

    private final RestTemplate restTemplate;

    @Value("${architecture-extraction.create-instance-endpoint}")
    public String architectureExtractionCreateInstanceEndpoint;

    @Autowired
    public ArchitectureExtractionService(ApiDocService apiDocService, InstanceProperties instanceProperties, ApplicationProperties applicationProperties) {
        this.apiDocService = apiDocService;
        this.instanceProperties = instanceProperties;
        this.applicationProperties = applicationProperties;

        restTemplate = new RestTemplate();
    }

    public void registerServiceInstance() throws UnknownHostException {
        try {
            restTemplate
                    .postForEntity(
                            architectureExtractionCreateInstanceEndpoint,
                            getServiceInstanceObj(),
                            String.class
                    );
        } catch (ResourceAccessException ignored) { System.out.println(ignored.getMessage()); }
    }

    private ServiceInstanceDto getServiceInstanceObj() throws UnknownHostException {
        return new ServiceInstanceDto(
                instanceProperties.getPort(),
                instanceProperties.getBasePath(),
                Set.of(instanceProperties.getProtocols()),
                instanceProperties.getContainerId(),
                instanceProperties.getTechnology(),
                instanceProperties.getRequestLogEndpoint(),
                LocalDateTime.now(),
                null,
                instanceProperties.getHost(),
                instanceProperties.getRegion(),
                getServiceObj()
        );
    }

    private ServiceDto getServiceObj() throws UnknownHostException {
        return new ServiceDto(
                applicationProperties.getTitle(),
                applicationProperties.getVersion(),
                applicationProperties.getDescription(),
                applicationProperties.getTechnologyStack(),
                applicationProperties.getCreationDate(),
                applicationProperties.getDomain(),
                applicationProperties.getContactPerson().get("name"),
                applicationProperties.getContactPerson().get("email"),
                apiDocService.getMethods()
        );
    }
}
