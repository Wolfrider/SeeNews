package com.oliver.seenews.base.util;

public class NumUtils {

    public static int toInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static int toInt(double value) {
        return (int)Math.round(value);
    }
}
