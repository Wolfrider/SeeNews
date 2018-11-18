package com.oliver.seenews.base.arch;

import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

public class LifecycleAdapter implements LifecycleObserver {

    private ILifecycleListener mLifecycleListener;

    public LifecycleAdapter(@NonNull ILifecycleListener lifecycleListener) {
        mLifecycleListener = lifecycleListener;
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    void onCreate() {
        mLifecycleListener.onCreate();
    }

    @OnLifecycleEvent(Event.ON_START)
    void onStart() {
        mLifecycleListener.onStart();
    }

    @OnLifecycleEvent(Event.ON_RESUME)
    void onResume() {
        mLifecycleListener.onResume();
    }

    @OnLifecycleEvent(Event.ON_PAUSE)
    void onPause() {
        mLifecycleListener.onPause();
    }

    @OnLifecycleEvent(Event.ON_STOP)
    void onStop() {
        mLifecycleListener.onStop();
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    void onDestroy() {
        mLifecycleListener.onDestroy();
    }
}

