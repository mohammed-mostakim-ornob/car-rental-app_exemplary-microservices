package de.uniba.dsg.microservice.architecture.extraction.managementservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class ApplicationInitBean {

    @Value("${logging.directory}")
    String logsPath;

    @EventListener
    public void init(ApplicationReadyEvent event) throws IOException {
        Files.createDirectories(Paths.get(logsPath));

        String filePath = logsPath + "/request.log";

        Files.writeString(Paths.get(filePath),
                "Hello World!\nHello World!",
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Result: " + Files.readString(Paths.get(filePath)));

        Files.writeString(Paths.get(filePath),
                "",
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Result: " + Files.readString(Paths.get(filePath)));
    }
}
