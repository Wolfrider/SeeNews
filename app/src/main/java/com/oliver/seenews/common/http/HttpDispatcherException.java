package com.oliver.seenews.common.http;

import android.support.annotation.NonNull;

public class HttpDispatcherException extends Exception {

    public ResponseData mResData;

    public HttpDispatcherException(@NonNull ResponseData responseData) {
        mResData = responseData;
    }

    public ResponseData getResponseData() {
        return mResData;
    }

    @Override
    public String toString() {
        return mResData.toString();
    }
}
