package de.uniba.dsg.carrental.carservice;

public class Constants {
    public static final String METHOD_GET_CAR = "getCar";
    public static final String METHOD_GET_CARS = "getCars";
    public static final String METHOD_GET_MANUFACTURERS = "getManufacturers";
    public static final String METHOD_GET_MANUFACTURER_CARS = "getManufacturerCars";

    public static final String HEADER_METHOD_NAME = "METHOD-NAME";
    public static final String HEADER_RESPONSE_CODE = "RESPONSE-CODE";
    public static final String HEADER_CLIENT_SERVICE_INSTANCE_ID = "CLIENT-SERVICE-INSTANCE-ID";

    public static final String ATTRIBUTE_REQUEST_START_TIME = "REQUEST-START-TIME";

    public static final String RESPONSE_STATUS_OK = "200";
    public static final String RESPONSE_STATUS_NOT_FOUND = "404";
    public static final String RESPONSE_STATUS_INTERNAL_SERVER_ERROR = "500";
}
