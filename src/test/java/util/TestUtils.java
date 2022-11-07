package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtils {
    public static <T> T convertJson(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
