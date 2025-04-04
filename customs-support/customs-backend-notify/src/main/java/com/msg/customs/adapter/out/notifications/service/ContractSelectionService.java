package com.msg.customs.adapter.out.notifications.service;

import com.msg.customs.adapter.out.notifications.api.model.CatalogResponse;
import com.msg.customs.adapter.out.notifications.api.model.DataSet;
import com.msg.customs.adapter.out.notifications.api.model.Distribution;
import com.msg.customs.adapter.out.notifications.api.model.Policy;
import com.msg.customs.adapter.out.notifications.config.RestProperties;
import com.msg.customs.adapter.out.notifications.model.ContractOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractSelectionService {

    private final RestProperties restProperties;

    public ContractOffer selectCustomsOffer(CatalogResponse catalog) {
        if(catalog.getDataSets() != null) {
            for (DataSet dataSet : catalog.getDataSets()) {
                if (dataSet.getAssetId().equals(restProperties.getOffer().getAssetId())) {
                    String assetId = dataSet.getAssetId();
                    String contractId = null;
                    String endpointUrl = null;
                    if (dataSet.getPolicies() != null) {
                        for (Policy policy : dataSet.getPolicies()) {
                            if (policy.getType().equals(restProperties.getOffer().getType())) {
                                contractId = policy.getId();
                                break;
                            }
                        }
                    }
                    if (dataSet.getDistributions() != null) {
                        for (Distribution distribution : dataSet.getDistributions()) {
                            if (distribution.getFormat().getId().equals(restProperties.getOffer().getFormat())) {
                                endpointUrl = distribution.getAccessService().getEndpoint();
                                break;
                            }
                        }
                    }
                    if (assetId != null && contractId != null && endpointUrl != null) {
                        return ContractOffer
                                .builder()
                                .assetId(assetId)
                                .id(contractId)
                                .endPointURL(endpointUrl)
                                .participantId(catalog.getParticipantId())
                                .build();
                    }
                }
            }
        }
        return null;
    }

}
