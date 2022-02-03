package de.uniba.dsg.carrental.locationservice;

public class Constants {
    public static final String METHOD_GET_LOCATION = "getLocation";
    public static final String METHOD_GET_LOCATIONS = "getLocations";
    public static final String METHOD_GET_DISTANCE = "getDistance";

    public static final String HEADER_METHOD_NAME = "METHOD-NAME";
    public static final String HEADER_RESPONSE_CODE = "RESPONSE-CODE";
    public static final String HEADER_CLIENT_SERVICE_INSTANCE_ID = "CLIENT-SERVICE-INSTANCE-ID";

    public static final String ATTRIBUTE_REQUEST_START_TIME = "REQUEST-START-TIME";

    public static final String RESPONSE_STATUS_OK = "200";
    public static final String RESPONSE_STATUS_BAD_REQUEST = "400";
    public static final String RESPONSE_STATUS_NOT_FOUND = "404";
    public static final String RESPONSE_STATUS_INTERNAL_SERVER_ERROR = "500";
}
