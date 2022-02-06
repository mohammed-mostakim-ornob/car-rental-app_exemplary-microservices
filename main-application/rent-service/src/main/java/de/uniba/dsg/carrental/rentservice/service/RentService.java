package de.uniba.dsg.carrental.rentservice.service;

import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.model.dto.Car;
import de.uniba.dsg.carrental.rentservice.model.dto.Distance;
import de.uniba.dsg.carrental.rentservice.model.dto.Rent;
import de.uniba.dsg.carrental.rentservice.architectureextraction.properties.InstanceProperties;
import de.uniba.dsg.carrental.rentservice.proxy.CarServiceProxy;
import de.uniba.dsg.carrental.rentservice.proxy.LocationServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final CarServiceProxy carServiceProxy;
    private final LocationServiceProxy locationServiceProxy;

    private final InstanceProperties instanceProperties;

    @Autowired
    public RentService(CarServiceProxy carServiceProxy, LocationServiceProxy locationServiceProxy, InstanceProperties instanceProperties) {
        this.carServiceProxy = carServiceProxy;
        this.locationServiceProxy = locationServiceProxy;
        this.instanceProperties = instanceProperties;
    }

    public Rent calculateRent(String carCode, String from, String to) throws EntityNotFoundException, InvalidRequestParamException {
        Car car = carServiceProxy.fetchCar(carCode, instanceProperties.getContainerId());
        Distance distance = locationServiceProxy.getDistance(from, to, instanceProperties.getContainerId());

        return new Rent(car.getCode(), distance.getFrom().getCode(), distance.getTo().getCode(), (car.getRentPerKilo() * distance.getDistance()));
    }
}
