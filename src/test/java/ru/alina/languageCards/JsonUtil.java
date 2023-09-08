package ru.alina.languageCards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;

public class JsonUtil {
    private static ObjectMapper getObjectMapper() {

        return new ObjectMapper()
                .registerModule(new JavaTimeModule());

    }
    public static String asJsonString(final Object obj) {
        try {

            return getObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        System.out.println("json   " + json);
        return getObjectMapper().readValue(json, clazz);
    }

    public static <T> ArrayList<T> jsonArrToObject(String json, Class<T> clazz) throws JsonProcessingException {
        ArrayList<T> arr = getObjectMapper().readValue(json, ArrayList.class);
        ArrayList<T> result = new ArrayList<>();
        for (T t : arr) {
            result.add(getObjectMapper().convertValue(t, clazz));
        }
        return result;
    }
}
