package com.pavikumbhar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonParserUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
