package de.uniba.dsg.carrental.rentservice.proxy;

import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.model.dto.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("car-service")
public interface CarServiceProxy {
    @GetMapping("/car-service/api/v1/cars/{code}")
    Car fetchCar(@PathVariable String code) throws EntityNotFoundException, InvalidRequestParamException;
}
