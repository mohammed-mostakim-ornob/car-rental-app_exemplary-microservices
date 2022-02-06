package de.uniba.dsg.carrental.locationservice.architectureextraction.controller;

import de.uniba.dsg.carrental.locationservice.architectureextraction.model.RequestLogDto;
import de.uniba.dsg.carrental.locationservice.architectureextraction.service.RequestLogService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${logging.get-request-logs.path}")
public class RequestLogsController {

    private final RequestLogService requestLogService;

    @Autowired
    public RequestLogsController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Hidden
    @GetMapping
    public ResponseEntity<?> getRequestLogs() {
        try {
            List<RequestLogDto> requestLogs = requestLogService.getRequestLogs();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requestLogs);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error.");
        }
    }
}
