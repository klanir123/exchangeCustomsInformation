package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CatalogResponse {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("dcat:dataset")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<DataSet> dataSets;

    @JsonProperty("dspace:participantId")
    private String participantId;

//    public ContractOffer selectCustomsOffer() {
//        if(dataSets != null) {
//            for (DataSet dataSet : dataSets) {
//                if (dataSet.getAssetId().equals("Customs")) {
//                    String assetId = dataSet.getAssetId();
//                    String contractId = null;
//                    String endpointUrl = null;
//                    if (dataSet.getPolicies() != null) {
//                        for (Policy policy : dataSet.getPolicies()) {
//                            if (policy.getType().equals("odrl:Offer")) {
//                                contractId = policy.getId();
//                                break;
//                            }
//                        }
//                    }
//                    if (dataSet.getDistributions() != null) {
//                        for (Distribution distribution : dataSet.getDistributions()) {
//                            if (distribution.getFormat().getId().equals("HttpData-Push")) {
//                                endpointUrl = distribution.getAccessService().getEndpoint();
//                                break;
//                            }
//                        }
//                    }
//                    if (assetId != null && contractId != null && endpointUrl != null) {
//                        return ContractOffer
//                                .builder()
//                                .assetId(assetId)
//                                .id(contractId)
//                                .endPointURL(endpointUrl)
//                                .participantId(participantId)
//                                .build();
//                    }
//                }
//            }
//        }
//        return null;
//    }

}
