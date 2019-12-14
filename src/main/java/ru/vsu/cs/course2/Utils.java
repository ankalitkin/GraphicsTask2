package ru.vsu.cs.course2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;;import java.io.IOException;

public class Utils {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String toJson(Picture picture) {
        try {
            return mapper.writeValueAsString(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
