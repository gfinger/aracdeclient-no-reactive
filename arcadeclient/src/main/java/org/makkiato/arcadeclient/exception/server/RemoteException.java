package org.makkiato.arcadeclient.exception.server;

import org.makkiato.arcadeclient.response.ErrorResponseBody;
import org.springframework.http.HttpStatusCode;

public class RemoteException extends ArcadedbServerException {

    public RemoteException(ErrorResponseBody body, HttpStatusCode status) {
        super(body, status);
    }

}
