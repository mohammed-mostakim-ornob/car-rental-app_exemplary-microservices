package de.uniba.dsg.carrental.rentservice.architectureextraction.helper;

import de.uniba.dsg.carrental.rentservice.architectureextraction.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ArchitectureExtractionHelper {

    private static ApplicationProperties applicationProperties;

    @Autowired
    public ArchitectureExtractionHelper(ApplicationProperties applicationProperties) {
        ArchitectureExtractionHelper.applicationProperties = applicationProperties;
    }

    public static HttpHeaders setHttpHeaders(Map<String, String> headerValues) {
        HttpHeaders httpHeaders = new HttpHeaders();

        headerValues.keySet().forEach(key -> {
            httpHeaders.set(key, headerValues.get(key));
        });

        return httpHeaders;
    }

    public static String buildMethodUniqueName(String methodName) {
        return applicationProperties.getUniqueId()
                + "_"
                + methodName;
    }
}
