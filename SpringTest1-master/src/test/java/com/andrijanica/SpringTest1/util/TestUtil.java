package com.andrijanica.SpringTest1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {
    private TestUtil() {
    }

    public static <T> T readResponse(String data, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(data, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
