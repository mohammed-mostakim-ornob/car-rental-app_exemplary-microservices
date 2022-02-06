package de.uniba.dsg.microservice.architecture.extraction.managementservice.controller;

import de.uniba.dsg.microservice.architecture.extraction.managementservice.model.dto.AggregatedRequestDto;
import de.uniba.dsg.microservice.architecture.extraction.managementservice.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management-service/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<?> addRequests(@RequestBody AggregatedRequestDto[] requests) {
        try {
            requestService.storeAggregatedRequests(requests);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Requests successfully added.");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Service Error");
        }
    }
}
