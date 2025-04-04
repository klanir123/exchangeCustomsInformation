package com.msg.customs.adapter.out.notifications.api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectNode createNode() {
        return MAPPER.createObjectNode();
    }

    public static ArrayNode createArray() {
        return MAPPER.createArrayNode();
    };


    public static String asString(Object value) {
        if (value instanceof String strValue) {
            return strValue;
        }
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


//    public static String asString(ArrayNode node) {
//        try {
//            return MAPPER.writeValueAsString(node);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
