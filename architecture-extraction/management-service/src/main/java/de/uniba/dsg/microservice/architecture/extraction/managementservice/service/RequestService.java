package de.uniba.dsg.microservice.architecture.extraction.managementservice.service;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto.AggregatedRequestDto;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Method;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Request;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.Response;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.entity.ServiceInstance;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.repository.MethodRepository;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.repository.RequestRepository;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.repository.ServiceInstanceRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RequestService {

    private final MethodRepository methodRepository;
    private final RequestRepository requestRepository;
    private final ServiceInstanceRepository serviceInstanceRepository;

    public RequestService(MethodRepository methodRepository, RequestRepository requestRepository, ServiceInstanceRepository serviceInstanceRepository) {
        this.methodRepository = methodRepository;
        this.requestRepository = requestRepository;
        this.serviceInstanceRepository = serviceInstanceRepository;
    }

    public void storeAggregatedRequests(AggregatedRequestDto[] aggregatedRequests) {
        Arrays.stream(aggregatedRequests).forEach(ar -> {
            Method method = getMethodByDescription(ar.getMethodId());
            Response response = method == null
                    ? null
                    : method.getResponses().stream()
                        .filter(x -> x.getCode() == Integer.parseInt(ar.getResponseCode()))
                        .findFirst()
                        .orElse(null);

            Request newRequest = new Request(
                    null,
                    ar.getCount(),
                    ar.getAverage(),
                    ar.getMaximum(),
                    ar.getMinimum(),
                    ar.getClientId().equals("EXTERNAL-CLIENT")
                            ? null
                            : getServiceInstanceByContainerId(ar.getClientId()),
                    getServiceInstanceByContainerId(ar.getServerId()),
                    method,
                    response
            );
            requestRepository.save(newRequest);
        });
    }

    private ServiceInstance getServiceInstanceByContainerId(String containerId) {
        return serviceInstanceRepository.findByContainerId(containerId).orElse(null);
    }

    private Method getMethodByDescription(String description) {
        return methodRepository.findMethodByDescription(description).orElse(null);
    }
}
