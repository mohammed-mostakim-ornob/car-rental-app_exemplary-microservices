package de.uniba.dsg.carrental.carservice.architectureextraction.service;

import de.uniba.dsg.carrental.carservice.architectureextraction.model.MethodDto;
import de.uniba.dsg.carrental.carservice.architectureextraction.model.ParameterDto;
import de.uniba.dsg.carrental.carservice.architectureextraction.model.ResponseDto;
import de.uniba.dsg.carrental.carservice.architectureextraction.model.SchemaDto;
import de.uniba.dsg.carrental.carservice.architectureextraction.helper.ArchitectureExtractionHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApiDocService {

    @Value("${server.port}")
    int serverPort;

    @Value("${springdoc.api-docs.path}")
    String docPath;

    private final RestTemplate restTemplate;

    public ApiDocService() {
        restTemplate = new RestTemplate();
    }

    public Set<MethodDto> getMethods() throws UnknownHostException {
        String docJson = restTemplate
                .getForEntity((getServerUrl() + docPath), String.class)
                .getBody();

        JSONObject docObj = new JSONObject(docJson);

        return extractMethods(docObj.getJSONObject("paths"));
    }

    private Set<MethodDto> extractMethods(JSONObject pathsObj) {
        Set<MethodDto> methods = new HashSet<>();

        pathsObj.keys().forEachRemaining(pathKey -> {
            JSONObject pathJson = pathsObj.getJSONObject(pathKey);
            pathJson.keys().forEachRemaining(methodKey -> {
                JSONObject methodJson = pathJson.getJSONObject(methodKey);

                methods.add(new MethodDto(
                        pathKey,
                        methodKey,
                        methodJson.getString("operationId"),
                        ArchitectureExtractionHelper.buildMethodUniqueName(methodJson.getString("operationId")),
                        methodJson.has("parameters")
                                ? extractParameters(methodJson.getJSONArray("parameters"))
                                : new HashSet<>(),
                        methodJson.has("responses")
                                ? extractResponses(methodJson.getJSONObject("responses"))
                                : new HashSet<>()
                ));
            });
        });

        return methods;
    }

    private Set<ParameterDto> extractParameters(JSONArray parametersJson) {
        Set<ParameterDto> parameters = new HashSet<>();

        parametersJson.forEach(item -> {
            JSONObject parameterJson = (JSONObject)item;
            parameters.add(new ParameterDto(
                    parameterJson.getString("name"),
                    parameterJson.getString("in"),
                    parameterJson.getString("name"),
                    parameterJson.getBoolean("required"),
                    (parameterJson.getJSONObject("schema").has("default")
                            ? parameterJson.getJSONObject("schema").getString("default")
                            : null),
                    extractSchema(parameterJson.getJSONObject("schema"))
            ));
        });

        return parameters;
    }

    private Set<ResponseDto> extractResponses(JSONObject responsesJson) {
        Set<ResponseDto> responses = new HashSet<>();

        responsesJson.keys().forEachRemaining(responseKey -> {
            JSONObject responseJson = responsesJson.getJSONObject(responseKey);

            responses.add(new ResponseDto(
                    Integer.parseInt(responseKey),
                    responseJson.getString("description"),
                    extractSchema(responseJson.getJSONObject("content").getJSONObject("*/*").getJSONObject("schema"))
            ));
        });

        return responses;
    }

    private SchemaDto extractSchema(JSONObject schemaJson) {
        return new SchemaDto(schemaJson.getString("type"), schemaJson.getString("type"));
    }

    private String getServerUrl() throws UnknownHostException {
        return "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort;
    }
}
