package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msg.customs.adapter.out.notifications.api.json.ContextDeserializer;
import com.msg.customs.adapter.out.notifications.api.json.ContextSerializer;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Context class which represents a context. Unfortunately the structure
 * context is not always a simple map of key-value pairs. In some cases
 * (jsonld) is directly mapped to the @context property.
 * <p>
 * As direct result a special {@link ContextSerializer Serializer} and
 * {@link ContextDeserializer Deserializer} is needed.
 * </p>
 */
@Data
@JsonSerialize(using = ContextSerializer.class)
@JsonDeserialize(using = ContextDeserializer.class)
public class Context {

    @JsonIgnore
    private boolean useArray = true;

    private Map<String, String> values = new HashMap<>();

    public static Context of() {
        return new Context();
    }

    public Context jsonld() {
        useArray = false;
        return add("jsonld",  "http://www.w3.org/ns/odrl.jsonld");
    }

    public Context vocab() {
        return add("@vocab", "https://w3id.org/edc/v0.0.1/ns/");
    }

    public Context add(String key, String value) {
        values.put(key, value);
        return this;
    }

    @JsonAnyGetter
    public Map<String, String> getValues() {
        return values;
    }

    @JsonAnySetter
    public void setValues(String key, String value) {
        values.put(key, value);
    }
}
