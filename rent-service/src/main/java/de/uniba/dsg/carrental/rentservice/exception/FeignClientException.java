package de.uniba.dsg.carrental.rentservice.exception;

public class FeignClientException extends Exception {
    public FeignClientException(String msg) {
        super(msg);
    }
}
