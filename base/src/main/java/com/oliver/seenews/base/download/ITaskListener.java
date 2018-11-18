package com.oliver.seenews.base.download;

public interface ITaskListener {

    void onStart();

    void onCancel();

    void onFail(int errorCode);

    void onProgress(int current, int total);

    void onFinish(boolean fromCache, long time, String filePath);
}
