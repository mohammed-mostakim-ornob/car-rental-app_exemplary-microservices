package de.uniba.dsg.carrental.rentservice.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }
}
