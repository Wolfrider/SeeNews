package com.oliver.seenews.base.download;

import android.support.annotation.NonNull;

public class Downloader {

    private static class Holder {
        static final Downloader INSTANCE = new Downloader();
    }

    private boolean mHasInit;
    private TaskQueue mQueue;

    private Downloader() {
    }

    public static Downloader getInstance() {
        return Holder.INSTANCE;
    }

    public void init(@NonNull Config config) {
        if (!mHasInit) {
            mQueue = new TaskQueue(config);
            mHasInit = true;
        }
    }

    public boolean addTask(@NonNull Task task) {
        return mQueue.add(task);
    }

}
