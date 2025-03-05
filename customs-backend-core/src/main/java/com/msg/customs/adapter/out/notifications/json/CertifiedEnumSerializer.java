package com.msg.customs.adapter.out.notifications.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.msg.customs.model.CertifiedEnum;

import java.io.IOException;

public class CertifiedEnumSerializer extends JsonSerializer<CertifiedEnum> {
    @Override
    public void serialize(CertifiedEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getCode());
    }
}
