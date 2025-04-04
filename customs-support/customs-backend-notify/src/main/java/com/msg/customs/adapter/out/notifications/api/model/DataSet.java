package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataSet {

    @JsonProperty("@id")
    private String assetId;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("odrl:hasPolicy")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Policy> policies;

    @JsonProperty("dcat:distribution")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Distribution> distributions;
}
