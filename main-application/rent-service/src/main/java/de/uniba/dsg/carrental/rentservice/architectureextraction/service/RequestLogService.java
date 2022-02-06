package de.uniba.dsg.carrental.rentservice.architectureextraction.service;

import de.uniba.dsg.carrental.rentservice.architectureextraction.model.RequestLogDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestLogService {

    @Value("${logging.directory}")
    String logsPath;

    @Value("${logging.request.file-name}")
    String fileName;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(logsPath));
        Files.createFile(Paths.get(getLogFilePath()));
    }

    public void storeRequest(RequestLogDto requestLogObj) throws IOException {
        String msg = requestLogObj.getTimestamp()
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

    public List<RequestLogDto> getRequestLogs() throws IOException {
        List<RequestLogDto> requestLogs = Files.lines(Paths.get(getLogFilePath()))
                .filter(rl -> !rl.isEmpty() && !rl.isBlank())
                .map(rl -> {

                    String[] parts = rl.split("\\|");

                    return new RequestLogDto(
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            Long.parseLong(parts[0]),
                            Long.parseLong(parts[5])
                    );
                }).collect(Collectors.toList());

        clearLogFile();

        return requestLogs;
    }

    private void clearLogFile() throws IOException {
        Files.writeString(Paths.get(getLogFilePath()),
                "",
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String getLogFilePath() {
        return logsPath + "/" + fileName;
    }
}
