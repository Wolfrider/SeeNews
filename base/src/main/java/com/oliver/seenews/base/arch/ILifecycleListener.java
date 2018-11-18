package com.oliver.seenews.base.arch;

public interface ILifecycleListener {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
