package io.github.tobero.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonUtility {

    public static void SaveObject(File file, Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T ReadObject(File file, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, clazz);
    }

    public static Object ReadObjectFromURL(URL url, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(url, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
