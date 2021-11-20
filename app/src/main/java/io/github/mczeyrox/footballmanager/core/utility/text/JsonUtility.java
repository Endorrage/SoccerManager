package io.github.mczeyrox.footballmanager.core.utility.text;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonUtility {

    public static void SaveObject(File file, Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String SaveObject(Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object ReadObject(String object, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(object, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object ReadObject(File file, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
