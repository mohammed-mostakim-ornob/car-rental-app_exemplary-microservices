package de.uniba.dsg.carrental.rentservice.proxy;

import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.model.dto.Distance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("location-service")
public interface LocationServiceProxy {
    @GetMapping("/location-service/api/v1/distance")
    Distance getDistance(@RequestParam String from, @RequestParam String to, @RequestHeader("CLIENT-SERVICE-INSTANCE-ID") String instanceId) throws EntityNotFoundException, InvalidRequestParamException;
}
