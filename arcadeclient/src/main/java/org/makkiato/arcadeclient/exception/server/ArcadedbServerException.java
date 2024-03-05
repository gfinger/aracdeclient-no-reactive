package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class ArcadedbServerException extends RuntimeException {
    private final ErrorResponseBody body;
    private final HttpStatusCode status;

    public ArcadedbServerException(ErrorResponseBody body, HttpStatusCode status) {
        super(body.error());
        this.body = body;
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public ErrorResponseBody getBody() {
        return body;
    }
}
