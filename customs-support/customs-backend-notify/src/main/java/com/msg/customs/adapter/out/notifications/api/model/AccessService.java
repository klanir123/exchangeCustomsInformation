package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessService {

    @JsonProperty("dcat:endpointDescription")
    private String description;

    @JsonProperty("dcat:endpointUrl")
    private String endpoint;

}
