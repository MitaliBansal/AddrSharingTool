package com.addrsharingtool.addrsharingtool.config;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomRestTemplate {

    private final RestTemplate restTemplate;

    public CustomRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          Class<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

}