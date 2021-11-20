package io.github.mczeyrox.footballmanager.core.utility;

import android.content.Context;
import android.content.Intent;

public class Utility {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void LaunchAcitvity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
