package com.oliver.seenews.base.util;

import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.content.PermissionChecker;

public class PermissionUtils {

    public static boolean hasGranted(String permission) {
        if (DeviceUtils.getSDKInt() < 23) {
            return true;
        } else {
            if (AppUtils.getTargetSDKInt() < 23) {
                return PackageManager.PERMISSION_GRANTED ==
                    PermissionChecker.checkSelfPermission(AppUtils.getApp(), permission);
            } else {
                return PackageManager.PERMISSION_GRANTED ==
                    AppUtils.getApp().checkPermission(permission, Process.myPid(), Process.myUid());
            }
        }

    }
}
