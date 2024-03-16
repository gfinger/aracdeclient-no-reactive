package org.makkiato.arcadeclient.base;

import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArcadeClient {
    private final RestClient restClient;
    private final String dbName;
    private final ObjectMapper mapper;
}
