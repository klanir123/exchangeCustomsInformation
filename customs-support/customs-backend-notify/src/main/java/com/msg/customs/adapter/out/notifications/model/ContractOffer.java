package com.msg.customs.adapter.out.notifications.model;

import com.msg.customs.adapter.out.notifications.api.model.Context;
import com.msg.customs.adapter.out.notifications.api.model.Policy;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractOffer {

    private final String id;

    private final String assetId;

    private final String endPointURL;

    private final String participantId;

    public Policy toPolicy() {
        return Policy.of()
                .id(getId())
                .type("Offer")
                .provider(getParticipantId())
                .assetId(getAssetId())
                .context(Context.of().jsonld())
                .build();
    }
}
