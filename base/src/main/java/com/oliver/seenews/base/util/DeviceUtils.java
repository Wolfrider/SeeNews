package com.oliver.seenews.base.util;

import android.Manifest.permission;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtils {

    private static String sImei;
    private static int sScreenWidth;
    private static int sScreenHeight;
    private static float sDensity;

    public static int getSDKInt() {
        return VERSION.SDK_INT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getImei() {
        if (TextUtils.isEmpty(sImei)) {
            TelephonyManager telephonyManager = (TelephonyManager)AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (PermissionUtils.hasGranted(permission.READ_PHONE_STATE)) {
                sImei = telephonyManager.getDeviceId();
            }
        }
        return sImei;
    }

    public static int getScreenWidth() {
        if (sScreenWidth <= 0 || sScreenHeight <= 0) {
            readScreenMetrics();
        }
        return sScreenWidth;
    }

    public static int getScreenHeight() {
        if (sScreenWidth <= 0 || sScreenHeight <= 0) {
            readScreenMetrics();
        }
        return sScreenHeight;
    }

    public static float getDensity() {
        if (sDensity <= 0) {
            readScreenMetrics();
        }
        return sDensity;
    }

    public static int dpToPx(int dp) {
        return Math.round(dp * getDensity());
    }

    private static void readScreenMetrics() {
        WindowManager windowManager = (WindowManager)AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (null != windowManager) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            sScreenWidth = displayMetrics.widthPixels;
            sScreenHeight = displayMetrics.heightPixels;
            sDensity = displayMetrics.density;
        }
    }
}
