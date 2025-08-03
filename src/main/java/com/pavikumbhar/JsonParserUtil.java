package com.pavikumbhar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.pavikumbhar.exception.AppException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonParserUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T parseJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new AppException("Failed to parse JSON", e);
        }
    }
}
