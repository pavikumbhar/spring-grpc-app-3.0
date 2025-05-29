package com.pavikumbhar.service;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProtobufJsonUtil {

    /**
     * Converts a Protobuf object to a JSON string.
     * @param message Protobuf object to be converted.
     * @return JSON string representation of the Protobuf object.
     * @throws RuntimeException if conversion fails.
     */
    public static String toJson(Message message) {
        try {
            return JsonFormat.printer().print(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Protobuf to JSON", e);
        }
    }

    /**
     * Converts a JSON string to a Protobuf object.
     * @param json JSON string to be converted.
     * @param builder Protobuf message builder to hold the parsed data.
     * @param <T> The type of the Protobuf object.
     * @return The Protobuf object created from the JSON string.
     * @throws RuntimeException if conversion fails.
     */
    public static <T extends Message> T fromJson(String json, Message.Builder builder) {
        try {
            JsonFormat.parser().merge(json, builder);
            return (T) builder.build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to Protobuf", e);
        }
    }

    /**
     * Converts a JSON string to a Protobuf object.
     * @param json JSON string to be converted.
     * @param builder Protobuf message builder to hold the parsed data.
     * @param <T> The type of the Protobuf object.
     * @return The Protobuf object created from the JSON string.
     * @throws RuntimeException if conversion fails.
     */
    public static <T extends Message> T fromJson(String json, Message.Builder builder, Class<T> clazz) {
        try {
            JsonFormat.parser().merge(json, builder);
            return clazz.cast(builder.build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to Protobuf", e);
        }
    }
}
