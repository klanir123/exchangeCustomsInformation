package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.customs.adapter.out.notifications.model.DataSpaceProtocol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class CatalogRequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String VOCAB_KEY = "@vocab";
    private static final String VOCAB_VALUE = "https://w3id.org/edc/v0.0.1/ns/";

    private static final String ADDRESS = "http://localhost:19194/protocol";

    private final static String CATALOG_REQUEST = """
            {
                "@context": {
                    "%s": "%s"
                 },
                 "counterPartyAddress": "%s",
                 "protocol": "dataspace-protocol-http"
            }
            """.formatted(VOCAB_KEY, VOCAB_VALUE, ADDRESS);

    @Test
    void testCreateRequest() throws JsonProcessingException {
        CatalogRequest request = new CatalogRequest();
        request.setCounterPartyAddress(ADDRESS);


        String s = MAPPER.writeValueAsString(request);

        assertTrue(JsonTestUtil.compareJsonStrings(CATALOG_REQUEST, s));
    }

    @Test
    void testReadRequest() throws JsonProcessingException {
        CatalogRequest request = MAPPER.readValue(CATALOG_REQUEST, CatalogRequest.class);

        assertEquals(ADDRESS, request.getCounterPartyAddress());
        assertEquals(DataSpaceProtocol.HTTP.getProtocol(), request.getProtocol());
        assertEquals(1, request.getContext().getValues().size());
        assertEquals(VOCAB_VALUE, request.getContext().getValues().get(VOCAB_KEY));
    }

}