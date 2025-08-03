package com.pavikumbhar.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Getter
@Component
public class LabelMapConfig {

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {
    };
    private final Map<String, Object> courseMap;
    private final Map<String, Object> certificationMap;
    private final Map<String, Object> programMap;

    public LabelMapConfig(@Value("${label.map.course}") String courseJson,
                          @Value("${label.map.certification}") String certificationJson,
                          @Value("${label.map.program}") String programJson,
                          ObjectMapper objectMapper) {
        this.courseMap = parseJson(courseJson, objectMapper, "courseMap");
        this.certificationMap = parseJson(certificationJson, objectMapper, "certificationMap");
        this.programMap = parseJson(programJson, objectMapper, "programMap");
    }

    private Map<String, Object> parseJson(String json, ObjectMapper objectMapper, String mapName) {
        try {
            if (json == null || json.trim().isEmpty()) {
                log.warn("JSON string for {} is empty or null. Returning an empty map.", mapName);
                return Collections.emptyMap();
            }
            return objectMapper.readValue(json, MAP_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON for {}: {}", mapName, e.getMessage(), e);
            throw new IllegalArgumentException("Invalid JSON for " + mapName, e);
        }
    }
}


/*
@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "label.map")
public class LabelMapConfig {

    private String course;
    private String certification;
    private String program;

    private Map<String, Object> courseMap;
    private Map<String, Object> certificationMap;
    private Map<String, Object> programMap;

    private final ObjectMapper objectMapper;

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {
    };


    @PostConstruct
    public void init() {
        this.courseMap = parseJson(course, "courseMap");
        this.certificationMap = parseJson(certification, "certificationMap");
        this.programMap = parseJson(program, "programMap");

        log.info("LabelMapConfig initialized: courseMap loaded ({} entries), certificationMap loaded ({} entries), programMap loaded ({} entries).",
                courseMap.size(), certificationMap.size(), programMap.size());
    }

    private Map<String, Object> parseJson(String json, String mapName) {
        if (json == null || json.trim().isEmpty()) {
            log.warn("JSON string for '{}' is null, empty, or consists only of whitespace. Returning an empty map.", mapName);
            return Map.of();
        }

        try {
            return objectMapper.readValue(json, MAP_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON for '{}'. Please ensure the corresponding configuration property ('label.map.{}') " +
                    "contains valid JSON. Error: {}", mapName, mapName.toLowerCase().replace("map", ""), e.getMessage(), e);
            throw new IllegalArgumentException("Invalid JSON configuration for '" + mapName + "'. Application startup failed.", e);
        }
    }
}

 */