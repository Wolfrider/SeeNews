package com.oliver.seenews.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.support.annotation.NonNull;

public class AssetUtils {

    private static final String TAG = "AssetUtils";

    public static String read(@NonNull String path) {
        StringBuilder sb = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(AppUtils.getApp().getAssets().open(path)))) {
                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.warn(TAG, ex.toString());
        }
        return sb.toString();
    }
}
