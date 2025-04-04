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
@Validated
public class RestProperties {

    @NotNull
    private String endpoint;

    private Offer offer;

    private Receiver receiver;

    @PostConstruct
    public void afterInit() {
        log.atInfo().addArgument(this).log("REST properties initialized: {}");
    }

    @Data
    @Validated
    public static class Offer {
        @NotNull
        private String assetId;
        @NotNull
        private String type;
        @NotNull
        private String format;
    }

    @Data
    @Validated
    public static class Receiver {
        @NotNull
        private String baseUrl;
        private String apiCatalogRequest = "/v3/catalog/request";
        private String apiContractNegotiations = "/v3/contractnegotiationss";
        private String apiContractAgreementCheck = "/v3/contractnegotiationss";

        public String urlCatalogRequest() {
            return url(baseUrl, apiCatalogRequest);
        }
        public String urlContractNegotiation() {
            return url(baseUrl, apiContractNegotiations);
        }
        public String urlContractAgreementCheck(String id) {
            String url = url(baseUrl, apiContractNegotiations);
            return attachPathParam(url, id);
        }

        private String attachPathParam(String url, String pathParam) {
            if (url.endsWith("/")) {
                return url + pathParam;
            } else {
                return url + "/" + pathParam;
            }
        }

        private String url(String baseUrl, String api) {
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            if (api.startsWith("/")) {
                api = api.substring(1);
            }
            return baseUrl + "/" + api;
        }

    }


}
