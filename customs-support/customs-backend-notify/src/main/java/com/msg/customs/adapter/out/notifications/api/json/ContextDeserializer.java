package com.msg.customs.adapter.out.notifications.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.msg.customs.adapter.out.notifications.api.model.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContextDeserializer extends JsonDeserializer<Context> {


    @Override
    public Context deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Context context = new Context();
        Map<String, String> values = new HashMap<>();

        JsonToken token = p.currentToken();
        if (token == JsonToken.START_OBJECT) {
            while (p.nextToken() != JsonToken.END_OBJECT) {
                String key = p.getCurrentName();
                p.nextToken();
                String value = p.getValueAsString();
                values.put(key, value);
            }
        } else if (token == JsonToken.VALUE_STRING) {
            values.put("@context", p.getValueAsString());
        }

        context.setValues(values);
        return context;
    }
}
