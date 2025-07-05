package com.payflow.payflow.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IdempotencyCreator {

    public static String create(Object data) {
        String source = data.toString();
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes).toString();
    }
}
