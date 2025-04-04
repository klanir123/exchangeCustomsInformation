package com.msg.customs.adapter.out.notifications.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractOffer {

    private final String id;

    private final String assetId;

    private final String endPointURL;

    private final String participantId;

}
