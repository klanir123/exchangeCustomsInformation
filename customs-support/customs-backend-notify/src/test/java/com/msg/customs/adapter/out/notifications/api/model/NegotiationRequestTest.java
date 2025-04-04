package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.customs.adapter.out.notifications.model.DataSpaceProtocol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegotiationRequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String VOCAB_KEY = "@vocab";
    private static final String VOCAB_VALUE = "https://w3id.org/edc/v0.0.1/ns/";
    private static final String TYPE = "ContractRequest";

    private static final String POLICY_ID = "MQ==:YXNzZXRJZA==:YzdkYWQwYWItNDJjYy00YWNjLWFiNjYtMDVkZmFiYTI4YjNj";
    private static final String ADDRESS = "http://localhost:19194/protocol";

    private static final String NEGOTIATION_REQUEST = """
            {
              "@context": {
                "@vocab": "https://w3id.org/edc/v0.0.1/ns/"
              },
              "@type": "ContractRequest",
              "counterPartyAddress": "%s",
              "protocol": "dataspace-protocol-http",
              "policy": {
                "@context": "http://www.w3.org/ns/odrl.jsonld",
                "@id": "MQ==:YXNzZXRJZA==:YzdkYWQwYWItNDJjYy00YWNjLWFiNjYtMDVkZmFiYTI4YjNj",
                "@type": "Offer",
                "assigner": "provider",
                "target": "assetId"
              }
            }
            """.formatted(ADDRESS);

    @Test
    void testCreateRequest() throws JsonProcessingException {
        Policy policy = Policy.of()
                .context(Context.of().jsonld())
                .id(POLICY_ID)
                .type("Offer")
                .provider("provider")
                .assetId("assetId")
                .build();

        NegotiationRequest request = new NegotiationRequest();
        request.setCounterPartyAddress(ADDRESS);
        request.setProtocol(DataSpaceProtocol.HTTP.getProtocol());
        request.setPolicy(policy);

        String s = MAPPER.writeValueAsString(request);

        assertTrue(JsonTestUtil.compareJsonStrings(NEGOTIATION_REQUEST, s));
    }

    @Test
    void testReadRequest() throws JsonProcessingException {
        NegotiationRequest request = MAPPER.readValue(NEGOTIATION_REQUEST, NegotiationRequest.class);

        assertEquals(ADDRESS, request.getCounterPartyAddress());
        assertEquals(TYPE, request.getType());
        assertEquals(DataSpaceProtocol.HTTP.getProtocol(), request.getProtocol());
        assertEquals(1, request.getContext().getValues().size());
        assertEquals(VOCAB_VALUE, request.getContext().getValues().get(VOCAB_KEY));
        assertNotNull(request.getPolicy());
        Policy policy = request.getPolicy();
        assertEquals(POLICY_ID, policy.getId());
        assertEquals("Offer", policy.getType());
        assertEquals("provider", policy.getProvider());
        assertEquals("assetId", policy.getAssetId());
    }

}