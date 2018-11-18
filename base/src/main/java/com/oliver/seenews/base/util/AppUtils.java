package com.oliver.seenews.base.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.oliver.seenews.base.SeeNewsApplication;

public class AppUtils {

    private static SeeNewsApplication sApp;
    private static int sVersionCode = -1;
    private static String sVersionName;
    private static String sProcessName;

    public static void init(@NonNull SeeNewsApplication app) {
        sApp = app;
    }

    public static SeeNewsApplication getApp() {
        return sApp;
    }

    public static boolean isDebug() {
        return (sApp.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static int getTargetSDKInt() {
        return sApp.getApplicationInfo().targetSdkVersion;
    }

    public static int getVersionCode() {
        if (sVersionCode <= 0) {
            readPackageInfo();
        }
        return sVersionCode;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(sVersionName)) {
            readPackageInfo();
        }
        return sVersionName;
    }

    public static String getProccessName() {
        if (TextUtils.isEmpty(sProcessName)) {
            ActivityManager activityManager = (ActivityManager)sApp.getSystemService(Context.ACTIVITY_SERVICE);
            if (null != activityManager) {
                for (ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
                    if (processInfo.pid == Process.myPid()) {
                        sProcessName = processInfo.processName;
                    }
                }
            }
        }
        return sProcessName;
    }

    private static void readPackageInfo() {
        try {
            PackageManager packageManager = sApp.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(sApp.getPackageName(), 0);
            sVersionCode = packageInfo.versionCode;
            sVersionName = packageInfo.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
