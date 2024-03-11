package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class ClientError extends ArcadeClientException {

    public ClientError(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
