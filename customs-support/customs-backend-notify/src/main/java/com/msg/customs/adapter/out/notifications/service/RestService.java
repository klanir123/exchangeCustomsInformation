package com.msg.customs.adapter.out.notifications.service;

import com.msg.customs.adapter.out.notifications.api.json.JsonUtil;
import com.msg.customs.adapter.out.notifications.config.RestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class RestService {

    private final RestProperties restProperties;

    private final RestTemplate restTemplate;

    public String post(String url, Object body) {
        ResponseEntity<String> response = postEntity(url, body);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to send request: " + response.getStatusCode());
    }

    public ResponseEntity<String> postEntity(String url, Object body) {
        String strBody = JsonUtil.asString(body);
        // Create Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create Request Entity
        HttpEntity<String> requestBody = new HttpEntity<>(strBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestBody, String.class);
    }

    public String get(String url) {
        ResponseEntity<String> response = getEntity(url);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to send request: " + response.getStatusCode());
    }

    public ResponseEntity<String> getEntity(String url) {
        return restTemplate.getForEntity(url, String.class);
    }


    public PostCallService fetchCatalog() {
        return new PostCallService(restProperties.getReceiver().urlCatalogRequest());
    }

    public PostCallService initiateContractNegotiation() {
        return new PostCallService(restProperties.getReceiver().urlContractNegotiation());
    }

    public GetCallService checkNegotiationStatus(String negotiationId) {
        return new GetCallService(restProperties.getReceiver().urlContractAgreementCheck(negotiationId));
    }

    @RequiredArgsConstructor
    public class PostCallService {
        private final String url;

        public String call(Object body) {
            return RestService.this.post(url, body);
        }

        public ResponseEntity<String> callEntity(Object body) {
            return RestService.this.postEntity(url, body);
        }

    }


    @RequiredArgsConstructor
    public class GetCallService {
        private final String url;

        public String call() {
            return RestService.this.get(url);
        }

        public ResponseEntity<String> callEntity() {
            return RestService.this.getEntity(url);
        }
    }
}
