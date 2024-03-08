package org.makkiato.arcadeclient.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class CachingHttpResponse implements ClientHttpResponse {
    private ClientHttpResponse delegate;
    private byte[] buffer;

    public CachingHttpResponse(ClientHttpResponse delegate) throws IOException {
        this.delegate = delegate;
        buffer = StreamUtils.copyToByteArray(delegate.getBody());
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(buffer);
    }

    @Override
    public HttpHeaders getHeaders() {
        return delegate.getHeaders();
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return delegate.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return delegate.getStatusText();
    }

    @Override
    public void close() {
        delegate.close();
    }
}
