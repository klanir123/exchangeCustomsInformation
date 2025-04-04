package com.msg.customs.adapter.out.notifications.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgreementResponseTest {

    private static final String ID = "a3a41846-c831-4fc1-afa5-77c104778bb6";
    private static final String TYPE = "ContractNegotiation";
    private static final String STATE = "FINALIZED";
    private static final String COUNTER_PARTY_ADDRESS = "http://localhost:19194/protocol";
    private static final String AGREEMENT_ID = "a3a41846-c831-4fc1-afa5-77c104778bb5";

    public static String AGREEMENT_RESPONSE = """
            {
                "@type": "ContractNegotiation",
                "@id": "%s",
                "type": "CONSUMER",
                "protocol": "dataspace-protocol-http",
                "state": "FINALIZED",
                "counterPartyId": "provider",
                "counterPartyAddress": "http://localhost:19194/protocol",
                "callbackAddresses": [],
                "createdAt": 1743605894999,
                "contractAgreementId": "%s",
                "@context": {
                    "@vocab": "https://w3id.org/edc/v0.0.1/ns/",
                    "edc": "https://w3id.org/edc/v0.0.1/ns/",
                    "odrl": "http://www.w3.org/ns/odrl/2/"
                }
            }
            """.formatted(ID, AGREEMENT_ID);

    @Test
    void testReadResponse() throws Exception {
        AgreementResponse response = JsonTestUtil.fromJson(AGREEMENT_RESPONSE, AgreementResponse.class);

        assertEquals(ID, response.getId());
        assertEquals(TYPE, response.getType());
        assertEquals(AGREEMENT_ID, response.getContractAgreementId());
        assertEquals(STATE, response.getState());
        assertEquals(COUNTER_PARTY_ADDRESS, response.getCounterPartyAddress());
    }

}