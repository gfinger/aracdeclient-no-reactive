package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class NotFoundException extends ArcadedbServerException {

    public NotFoundException(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
