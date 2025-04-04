package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.adapter.out.notifications.model.DataSpaceProtocol;
import lombok.Data;

@Data
public class CatalogRequest {

    @JsonProperty("@context")
    private Context context;

    @JsonProperty("counterPartyAddress")
    private String counterPartyAddress;

    @JsonProperty("protocol")
    private String protocol;

    public CatalogRequest() {
        setContext(Context.of().vocab());
        setProtocol(DataSpaceProtocol.HTTP.getProtocol());
    }

    public static Builder withDefaults() {
        return new Builder();
    }

    public static class Builder {
        private final CatalogRequest catalogRequest;

        public Builder() {
            this.catalogRequest = new CatalogRequest();
        }

        public Builder counterPartyAddress(String counterPartyAddress) {
            this.catalogRequest.setCounterPartyAddress(counterPartyAddress);
            return this;
        }

        public Builder protocol(String protocol) {
            this.catalogRequest.setProtocol(protocol);
            return this;
        }

        public CatalogRequest build() {
            return this.catalogRequest;
        }
    }

}
