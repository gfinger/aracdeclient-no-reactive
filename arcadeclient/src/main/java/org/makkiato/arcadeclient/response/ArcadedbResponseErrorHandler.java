package org.makkiato.arcadeclient.response;

import java.io.IOException;

import org.makkiato.arcadeclient.exception.server.IllegalArgumentException;
import org.makkiato.arcadeclient.exception.server.NotFoundException;
import org.makkiato.arcadeclient.exception.server.RemoteException;
import org.makkiato.arcadeclient.exception.server.SecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ArcadedbResponseErrorHandler implements ResponseErrorHandler {
    ObjectMapper mapper;

    public ArcadedbResponseErrorHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().equals(HttpStatus.OK);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundException(new ErrorResponseBody(null, null, null, null), HttpStatus.NOT_FOUND);
        }

        ErrorResponseBody error = mapper.readValue(response.getBody(), ErrorResponseBody.class);
        throw switch (error.exception()) {
            case "com.arcadedb.server.security.ServerSecurityException" ->
                new SecurityException(error, response.getStatusCode());
            case "java.lang.IllegalArgumentException" ->
                new IllegalArgumentException(error, response.getStatusCode());
            default -> new RemoteException(error, response.getStatusCode());
        };
    }

}
