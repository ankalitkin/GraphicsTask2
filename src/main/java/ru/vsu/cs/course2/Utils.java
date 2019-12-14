package ru.vsu.cs.course2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;;import java.io.*;

public class Utils {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void saveToFile(String filename, Picture picture) {
        try {
            OutputStream stream = new FileOutputStream(filename);
            mapper.writeValue(stream, picture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Picture loadFromFile(String filename) {
        try {
            InputStream stream = new FileInputStream(filename);
            return mapper.readValue(stream, Picture.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
