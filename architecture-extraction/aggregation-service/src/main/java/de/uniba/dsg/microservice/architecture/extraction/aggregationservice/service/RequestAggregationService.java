package de.uniba.dsg.microservice.architecture.extraction.aggregationservice.service;

import de.uniba.dsg.microservice.architecture.extraction.aggregationservice.model.dto.AggregatedRequestDto;
import de.uniba.dsg.microservice.architecture.extraction.aggregationservice.model.dto.RequestLogDto;
import de.uniba.dsg.microservice.architecture.extraction.aggregationservice.model.dto.ServiceInstanceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
public class RequestAggregationService {
    private final RestTemplate restTemplate;

    @Value("${management-service.add-requests-endpoint}")
    private String addRequestsEndpoint;

    @Value("${management-service.get-instances-endpoint}")
    private String getInstancesEndpoint;

    public RequestAggregationService(){
        restTemplate = new RestTemplate();
    }

    public void aggregateRequests() {
        List<AggregatedRequestDto> aggregatedRequests = new ArrayList<>();

        ServiceInstanceDto[] instances = getInstances();

        for (ServiceInstanceDto ins : instances) {
            aggregatedRequests.addAll(aggregatedRequests(retrieveInstanceRequestLogs(ins)));
        }

        sendAggregatedQueriesToManagementService(aggregatedRequests);
    }

    private ServiceInstanceDto[] getInstances() {
        try {
            return restTemplate
                    .getForEntity(getInstancesEndpoint, ServiceInstanceDto[].class)
                    .getBody();
        } catch (RestClientException ex) {
            return new ServiceInstanceDto[0];
        }
    }

    private RequestLogDto[] retrieveInstanceRequestLogs(ServiceInstanceDto instance) {
        try {
            return restTemplate
                    .getForEntity((instance.getBasePath() + instance.getRequestLogEndpoint()), RequestLogDto[].class)
                    .getBody();
        } catch (Exception ex) {
            return new RequestLogDto[0];
        }
    }

    private List<AggregatedRequestDto> aggregatedRequests(RequestLogDto[] requestLogs) {
        List<AggregatedRequestDto> aggregatedRequests = new ArrayList<>();

        Map<RequestLogDto.RequestLogKeys, List<RequestLogDto>> aggregatedRequestMap = Arrays.stream(requestLogs)
                .collect(groupingBy(rl -> new RequestLogDto.RequestLogKeys(rl.getClientId(), rl.getServerId(), rl.getMethodId(), rl.getResponseCode())));

        aggregatedRequestMap.forEach((key, value) -> aggregatedRequests.add(new AggregatedRequestDto(
                key.clientId(),
                key.serverId(),
                key.methodId(),
                key.responseCode(),
                (long) value.size(),
                value.stream().mapToLong(RequestLogDto::getResponseTime).average().orElse(0),
                value.stream().mapToLong(RequestLogDto::getResponseTime).max().orElse(0),
                value.stream().mapToLong(RequestLogDto::getResponseTime).min().orElse(0)
        )));

        return aggregatedRequests;
    }

    private void sendAggregatedQueriesToManagementService(List<AggregatedRequestDto> aggregatedRequests) {
        try {
            restTemplate
                    .postForEntity(addRequestsEndpoint, aggregatedRequests, String.class)
                    .getBody();
        } catch (RestClientException ignored) {
        }
    }
}
