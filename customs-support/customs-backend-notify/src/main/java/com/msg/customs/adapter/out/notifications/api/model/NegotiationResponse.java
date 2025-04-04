package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NegotiationResponse {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;
}
