package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class IllegalArgumentException extends ArcadedbServerException {

    public IllegalArgumentException(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
