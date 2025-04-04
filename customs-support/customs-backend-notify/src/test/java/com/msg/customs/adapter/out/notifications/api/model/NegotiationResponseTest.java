package com.msg.customs.adapter.out.notifications.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegotiationResponseTest {

    private static final String TYPE = "IdResponse";
    private static final String ID = "0baa4f1b-e91c-482f-bb31-ca85b619087f";

    private static final String NEGOTIATION_RESPONSE = """
            {
                "@type": "IdResponse",
                "@id": "%s",
                "createdAt": 1743427092232,
                "@context": {
                    "@vocab": "https://w3id.org/edc/v0.0.1/ns/",
                    "edc": "https://w3id.org/edc/v0.0.1/ns/",
                    "odrl": "http://www.w3.org/ns/odrl/2/"
                }
            }
            """.formatted(ID);

    @Test
    void testReadResponse() throws Exception {
        NegotiationResponse response = JsonTestUtil.fromJson(NEGOTIATION_RESPONSE, NegotiationResponse.class);

        assertEquals(TYPE, response.getType());
        assertEquals(ID, response.getId());
    }
}