package de.uniba.dsg.carrental.locationservice.bean;

import de.uniba.dsg.carrental.locationservice.service.ArchitectureExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class ApplicationInitBean {

    private final ArchitectureExtractionService architectureExtractionService;

    @Autowired
    public ApplicationInitBean(ArchitectureExtractionService architectureExtractionService) {
        this.architectureExtractionService = architectureExtractionService;
    }

    @EventListener
    public void init(ApplicationReadyEvent event) throws UnknownHostException {
        architectureExtractionService.registerServiceInstance();
    }
}
