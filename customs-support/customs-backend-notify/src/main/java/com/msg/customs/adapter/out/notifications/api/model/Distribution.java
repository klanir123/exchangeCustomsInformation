package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Distribution {

    @JsonProperty("dct:format")
    private DistributionFormat format;

    @JsonProperty("dcat:accessService")
    private AccessService accessService;
}
