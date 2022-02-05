package de.uniba.dsg.carrental.carservice.service;

import de.uniba.dsg.carrental.carservice.model.dto.architectureextraction.RequestLogDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class RequestLogService {

    @Value("${logging.directory}")
    String logsPath;

    @Value("${logging.request.file-name}")
    String fileName;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(logsPath));
    }

    public void storeRequest(RequestLogDto requestLogObj) throws IOException {
        String msg = requestLogObj.getResponseTime()
                + "|" + (requestLogObj.getClientId() == null ? "EXTERNAL-CLIENT" : requestLogObj.getClientId())
                + "|" + requestLogObj.getServerId()
                + "|" + requestLogObj.getMethodId()
                + "|" + requestLogObj.getResponseCode()
                + "|" + requestLogObj.getResponseTime()
                + "\n";

        Files.writeString(Paths.get(logsPath + "/" + fileName),
                msg,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
