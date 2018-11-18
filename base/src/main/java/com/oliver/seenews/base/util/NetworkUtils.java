package com.oliver.seenews.base.util;

import java.util.LinkedList;
import java.util.List;

import android.Manifest;
import android.Manifest.permission;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import com.oliver.seenews.base.Constant.Network;

public class NetworkUtils {

    public interface IStateChangedListener {

        void onChanged(int from, int to);
    }

    private static int sNetworkType;
    private static List<IStateChangedListener> sChangedListeners = new LinkedList<>();

    public static void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        AppUtils.getApp().registerReceiver(new NetworkReceiver(), intentFilter);
        readNetworkState();
    }

    public boolean hasInternet() {
        return Network.NET_NO_CONNECT != sNetworkType;
    }

    public boolean isWifi() {
        return Network.NET_WIFI == sNetworkType;
    }

    public boolean isMobile() {
        return Network.NET_MOBILE == sNetworkType;
    }

    public static void registerListener(@NonNull IStateChangedListener listener) {
        sChangedListeners.add(listener);
    }

    public static void unRegisterListener(@NonNull IStateChangedListener listener) {
        sChangedListeners.remove(listener);
    }

    public static void unRegisterListeners() {
        sChangedListeners.clear();
    }

    private static void readNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager)AppUtils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            if (PermissionUtils.hasGranted(permission.ACCESS_NETWORK_STATE)) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (null != networkInfo && networkInfo.isConnected()) {
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            sNetworkType = Network.NET_WIFI;
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            sNetworkType = Network.NET_MOBILE;
                            break;
                        default:
                            sNetworkType = Network.NET_OTHER;
                            break;
                    }
                } else {
                    sNetworkType = Network.NET_NO_CONNECT;
                }
            }
        }
    }

    private static void notifyStateChanged(int from, int to) {
        for (IStateChangedListener listener : sChangedListeners) {
            listener.onChanged(from, to);
        }
    }

    public static class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                int prevType = sNetworkType;
                readNetworkState();
                if (prevType != sNetworkType) {
                    notifyStateChanged(prevType, sNetworkType);
                }
            }
        }
    }
}
