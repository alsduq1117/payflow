package com.payflow.payflow.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class IdempotencyCreator {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private IdempotencyCreator() {}

    public static String create(Object data) {
        try {
            String source = MAPPER.writeValueAsString(data);
            byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
            return UUID.nameUUIDFromBytes(bytes).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create idempotency key", e);
        }
    }
}