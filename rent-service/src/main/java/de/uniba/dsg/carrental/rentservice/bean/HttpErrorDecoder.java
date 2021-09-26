package de.uniba.dsg.carrental.rentservice.bean;

import de.uniba.dsg.carrental.rentservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.rentservice.exception.InvalidRequestParamException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.status() == 400) return new InvalidRequestParamException(readMessage(response));
            if (response.status() == 404) return new EntityNotFoundException(readMessage(response));

            return new Exception("Unknown server error.");
        } catch (IOException ex) {
            return new Exception("Error reading response.");
        }
    }

    private String readMessage(Response response) throws IOException {
        return IOUtils.toString(response.body().asInputStream(), "UTF-8");
    }
}
