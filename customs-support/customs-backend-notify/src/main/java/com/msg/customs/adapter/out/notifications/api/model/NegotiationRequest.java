package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.adapter.out.notifications.model.DataSpaceProtocol;
import lombok.Data;

@Data
public class NegotiationRequest {

    @JsonProperty("@context")
    private Context context;

    @JsonProperty("@type")
    private String type;

    private String counterPartyAddress;

    private String protocol;

    private Policy policy;

    public NegotiationRequest() {
        setContext(Context.of().vocab());
        setType("ContractRequest");
        setProtocol(DataSpaceProtocol.HTTP.getProtocol());
    }

    public static Builder withDefaults() {
        return new Builder();
    }

    public static class Builder {
        private final NegotiationRequest negotiationRequest;

        public Builder() {
            this.negotiationRequest = new NegotiationRequest();
        }

        public Builder counterPartyAddress(String counterPartyAddress) {
            this.negotiationRequest.setCounterPartyAddress(counterPartyAddress);
            return this;
        }

        public Builder protocol(String protocol) {
            this.negotiationRequest.setProtocol(protocol);
            return this;
        }

        public Builder policy(Policy policy) {
            this.negotiationRequest.setPolicy(policy);
            return this;
        }

        public NegotiationRequest build() {
            return this.negotiationRequest;
        }

    }
}
