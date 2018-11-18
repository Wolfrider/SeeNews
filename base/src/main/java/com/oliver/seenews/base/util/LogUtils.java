package com.oliver.seenews.base.util;

import android.support.annotation.NonNull;
import android.util.Log;

public class LogUtils {
    private static final String TAG = "SeeNews";

    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;

    private static int sLevel = VERBOSE;

    public static void setLogLevel(int level) {
        sLevel = level;
    }

    public static void verbose(@NonNull String subTag, @NonNull String msg) {
        if (canOutput(VERBOSE)) {
            Log.v(TAG, makeMsg(subTag, msg));
        }
    }

    public static void debug(@NonNull String subTag, @NonNull String msg) {
        if (canOutput(DEBUG)) {
            Log.d(TAG, makeMsg(subTag, msg));
        }
    }

    public static void info(@NonNull String subTag, @NonNull String msg) {
        if (canOutput(INFO)) {
            Log.i(TAG, makeMsg(subTag, msg));
        }
    }

    public static void warn(@NonNull String subTag, @NonNull String msg) {
        if (canOutput(WARN)) {
            Log.w(TAG, makeMsg(subTag, msg));
        }
    }

    public static void error (@NonNull String subTag, @NonNull String msg) {
        if (canOutput(ERROR)) {
            Log.e(TAG, makeMsg(subTag, msg));
        }
    }

    private static String makeMsg(String subTag, String msg) {
        return String.format("[%s] %s", subTag, msg);
    }

    private static boolean canOutput(int level) {
        return level >= sLevel;
    }
}
