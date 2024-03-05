package org.makkiato.arcadeclient.response;

public record ErrorResponseBody(String error, String detail, String exception, String exceptionArgs) {

}
