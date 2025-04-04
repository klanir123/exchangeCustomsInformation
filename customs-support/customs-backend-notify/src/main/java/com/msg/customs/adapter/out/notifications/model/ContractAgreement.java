package com.msg.customs.adapter.out.notifications.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractAgreement {

    private String id;

    private String dataProviderUrl;

    private String agreementId;

}
