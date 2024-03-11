package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class ServerError extends ArcadeClientException {

    public ServerError(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
