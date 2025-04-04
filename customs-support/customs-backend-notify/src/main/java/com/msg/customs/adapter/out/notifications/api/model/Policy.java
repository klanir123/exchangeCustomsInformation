package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Policy {

    @JsonProperty("@context")
    private Context context;

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("assigner")
    private String provider;

    @JsonProperty("target")
    private String assetId;

//    public static Builder of(ContractOffer contractOffer) {
//        return of()
//                .id(contractOffer.getId())
//                .type("Offer")
//                .provider(contractOffer.getParticipantId())
//                .assetId(contractOffer.getAssetId())
//                .context(Context.of().jsonld());
//    }

    public static Builder of() {
        return new Policy.Builder();
    }

    public static class Builder {
        private Policy policy = new Policy();

        public Builder id(String id) {
            this.policy.setId(id);
            return this;
        }
        public Builder type(String type) {
            this.policy.setType(type);
            return this;
        }
        public Builder provider(String provider) {
            this.policy.setProvider(provider);
            return this;
        }
        public Builder assetId(String assetId) {
            this.policy.setAssetId(assetId);
            return this;
        }
        public Builder context(Context context) {
            this.policy.setContext(context);
            return this;
        }
        public Policy build() {
            return this.policy;
        }
    }
}
