package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTestUtil {

    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    public static boolean compareJsonStrings(String json1, String json2) throws JsonProcessingException {
        JsonNode tree1 = MAPPER.readTree(json1);
        JsonNode tree2 = MAPPER.readTree(json2);
        return tree1.equals(tree2);
    }

}
