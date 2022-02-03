package de.uniba.dsg.carrental.carservice.service;

import de.uniba.dsg.carrental.carservice.model.data.Log;
import de.uniba.dsg.carrental.carservice.properties.InstanceProperties;
import de.uniba.dsg.carrental.carservice.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestLogService {

    private final LogRepository logRepository;
    private final InstanceProperties instanceProperties;

    public RequestLogService(LogRepository logRepository, InstanceProperties instanceProperties) {
        this.logRepository = logRepository;
        this.instanceProperties = instanceProperties;
    }

    public void storeRequest(String clientId, String methodId, String responseCode, Long startTime, Long responseTime) {
        Log requestLog = new Log(
                null,
                instanceProperties.getContainerId(),
                clientId == null ? "EXTERNAL-CLIENT" : clientId,
                methodId,
                responseCode,
                startTime,
                responseTime
        );
        logRepository.save(requestLog);
    }
}
