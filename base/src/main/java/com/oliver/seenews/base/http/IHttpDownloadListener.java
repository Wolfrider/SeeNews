package com.oliver.seenews.base.http;

public interface IHttpDownloadListener {

    void onStart(int totalSize);

    void onProgress(int growSize);

    void onFail(int errorCode);

    void onFinish();
}
