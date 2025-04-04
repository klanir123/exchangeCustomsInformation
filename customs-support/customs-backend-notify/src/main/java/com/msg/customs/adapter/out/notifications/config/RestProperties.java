package com.msg.customs.adapter.out.notifications.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "edc")
@Data
@Slf4j
public class RestProperties {

    private String version;

    private Receiver receiver = new Receiver();

    @PostConstruct
    public void afterInit() {
        log.atInfo().addArgument(this).log("REST properties initialized: {}");
    }

    @Data
    @Validated
    public class Receiver {
        private String protocol = "http";
        @NotNull
        private String baseUrl;
        private String apiCatalogRequest = "/v3/catalog/request";
        private String apiContractNegotiations = "/v3/contractnegotiationss";
        private String apiContractAgreementCheck = "/v3/contractnegotiationss";

        public String urlCatalogRequest() {
            return url( protocol, baseUrl, apiCatalogRequest);
        }
        public String urlContractNegotiation() {
            return url( protocol, baseUrl, apiContractNegotiations);
        }
        public String urlContractAgreementCheck(String id) {
            String url = url(protocol, baseUrl, apiContractNegotiations);
            return attachPathParam(url, id);
        }
    }

    private String attachPathParam(String url, String pathParam) {
        if (url.endsWith("/")) {
            return url + pathParam;
        } else {
            return url + "/" + pathParam;
        }
    }

    private String url(String protocol, String baseUrl, String api) {
        String rest = baseUrl + "/" + api;
        // If baseUrl ends with / and api starts with /, remove one /
        rest = rest.replace("///", "/");
        rest = rest.replace("//", "/");

        String url =  protocol + "://" + rest;
        // If protocol ends with :// remove one ://
        return url.replace("://://", "://");
    }
}
