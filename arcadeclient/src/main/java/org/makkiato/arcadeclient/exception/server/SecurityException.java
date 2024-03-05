package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class SecurityException extends ArcadedbServerException {
    public SecurityException(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }
}
