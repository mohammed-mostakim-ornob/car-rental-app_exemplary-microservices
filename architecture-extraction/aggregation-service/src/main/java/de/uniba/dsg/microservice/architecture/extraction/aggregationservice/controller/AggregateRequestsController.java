package de.uniba.dsg.microservice.architecture.extraction.aggregationservice.controller;

import de.uniba.dsg.microservice.architecture.extraction.aggregationservice.service.RequestAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aggregation-service")
public class AggregateRequestsController {

    private final RequestAggregationService requestAggregationService;

    @Autowired
    public AggregateRequestsController(RequestAggregationService requestAggregationService) {
        this.requestAggregationService = requestAggregationService;
    }

    @GetMapping
    public ResponseEntity<?> aggregateRequests() {
        try {
            requestAggregationService.aggregateRequests();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Requests Successfully aggregated");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error.");
        }
    }
}
