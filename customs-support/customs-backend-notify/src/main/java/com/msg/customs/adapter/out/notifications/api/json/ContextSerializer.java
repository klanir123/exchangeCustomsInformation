package com.msg.customs.adapter.out.notifications.api.json;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.msg.customs.adapter.out.notifications.api.model.Context;

import java.io.IOException;
import java.util.Map;

public class ContextSerializer extends JsonSerializer<Context> {

    @Override
    public void serialize(Context context, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        if (context.isUseArray()) {
            gen.writeStartObject();
            for (Map.Entry<String, String> entry : context.getValues().entrySet()) {
                gen.writeStringField(entry.getKey(), entry.getValue());
            }
            gen.writeEndObject();
        }
        else {
            Map.Entry<String, String> first = context.getValues().entrySet().stream().findFirst().get();
            gen.writeString(first.getValue());
        }
    }
}
