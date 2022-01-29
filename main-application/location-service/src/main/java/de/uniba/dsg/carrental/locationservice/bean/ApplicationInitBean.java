package de.uniba.dsg.carrental.locationservice.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ApplicationInitBean {

    @Value("${server.port}")
    int serverPort;

    @Value("${springdoc.api-docs.path}")
    String docPath;

    @EventListener
    public void init(ApplicationReadyEvent event) throws UnknownHostException {
        String docJson = new RestTemplate()
                .getForEntity((getServerUrl() + docPath), String.class)
                .getBody();

        System.out.println(docJson);
    }

    private String getServerUrl() throws UnknownHostException {
        return "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort;
    }
}
