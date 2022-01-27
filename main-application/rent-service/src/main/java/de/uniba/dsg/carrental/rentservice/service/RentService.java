package de.uniba.dsg.carrental.rentservice.service;

import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import de.uniba.dsg.carrental.rentservice.model.dto.Car;
import de.uniba.dsg.carrental.rentservice.model.dto.Distance;
import de.uniba.dsg.carrental.rentservice.model.dto.Rent;
import de.uniba.dsg.carrental.rentservice.proxy.CarServiceProxy;
import de.uniba.dsg.carrental.rentservice.proxy.LocationServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final CarServiceProxy carServiceProxy;
    private final LocationServiceProxy locationServiceProxy;

    @Autowired
    public RentService(CarServiceProxy carServiceProxy, LocationServiceProxy locationServiceProxy) {
        this.carServiceProxy = carServiceProxy;
        this.locationServiceProxy = locationServiceProxy;
    }

    public Rent calculateRent(String carCode, String from, String to) throws EntityNotFoundException, InvalidRequestParamException {
        Car car = carServiceProxy.fetchCar(carCode);
        Distance distance = locationServiceProxy.getDistance(from, to);

        return new Rent(car.getCode(), distance.getFrom().getCode(), distance.getTo().getCode(), (car.getRentPerKilo() * distance.getDistance()));
    }
}
