package io.github.mczeyrox.footballmanager.core.utility.text;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextUtility {

    public static void save(Object object, String filename, Context context) throws IOException {
        saveToInternalStorage(JsonUtility.SaveObject(object), filename, context);
    }

    public static Object read(String filename, Context context, Class clazz) throws IOException {
        if (!exists(filename, context)) return null;
        return JsonUtility.ReadObject(readFileFromInternalStorage(filename, context), clazz);
    }

    public static boolean exists(String filename, Context context) {
        File dir = context.getFilesDir();
        File file = new File(dir, filename);
        return file.exists();
    }

    public static boolean saveToInternalStorage(String data, String filename, Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        //default mode is PRIVATE, can be APPEND etc.
        fos.write(data.getBytes());
        fos.close();
        return true;
    }

    public static String readFileFromInternalStorage(String filename, Context context) throws IOException {
        FileInputStream fis = context.openFileInput(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
}
