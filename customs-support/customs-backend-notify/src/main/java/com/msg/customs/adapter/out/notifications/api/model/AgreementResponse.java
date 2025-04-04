package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AgreementResponse {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;

    private String protocol;
    private String state;
    private String counterPartyAddress;
    private long createdAt;
    private String contractAgreementId;
}
