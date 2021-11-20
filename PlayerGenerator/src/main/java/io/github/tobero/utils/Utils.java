package io.github.tobero.utils;

import static javax.swing.JOptionPane.showMessageDialog;

public class Utils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static ObjectPair<Boolean, Integer> getInt(String str) {
        int value = -1;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e){
            return new ObjectPair<Boolean, Integer>(false, value);
        }
        return new ObjectPair<Boolean, Integer>(true, value);
    }

    public static float getFloat(String str) {
        float value = -1;
        try {
            value = Float.parseFloat(str);
        } catch (NumberFormatException e){
            showMessageDialog(null, "Coudn't convert " + str + " to float");
            return 0;
        }
        return value;
    }
}
