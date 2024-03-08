package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class BadRequestException extends ArcadedbServerException {

    public BadRequestException(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
