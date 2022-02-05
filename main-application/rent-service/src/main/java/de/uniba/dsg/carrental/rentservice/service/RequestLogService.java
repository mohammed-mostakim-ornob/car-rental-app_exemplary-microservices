package de.uniba.dsg.carrental.rentservice.service;

import de.uniba.dsg.carrental.rentservice.properties.InstanceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class RequestLogService {

    private final InstanceProperties instanceProperties;

    @Value("${logging.directory}")
    String logsPath;

    @Value("${logging.request.file-name}")
    String fileName;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(logsPath));
    }

    public RequestLogService(InstanceProperties instanceProperties) {
        this.instanceProperties = instanceProperties;
    }

    public void storeRequest(String clientId, String methodId, String responseCode, Long startTime, Long responseTime) throws IOException {
        String msg = startTime
                + "|" + (clientId == null ? "EXTERNAL-CLIENT" : clientId)
                + "|" + instanceProperties.getContainerId()
                + "|" + methodId
                + "|" + responseCode
                + "|" + responseTime
                + "\n";

        Files.writeString(Paths.get(logsPath + "/" + fileName),
                msg,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
