package com.oliver.seenews.base.http;

import android.support.annotation.NonNull;

public class HttpDownloadRequest extends HttpRequest {

    private String mFilePath;

    protected HttpDownloadRequest(Builder builder) {
        super(builder);
        mFilePath = builder.mFilePath;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public static class Builder extends HttpRequest.Builder {

        private String mFilePath;

        public Builder setFilePath(@NonNull String filePath) {
            mFilePath = filePath;
            return this;
        }

        @Override
        public HttpDownloadRequest build() {
            return new HttpDownloadRequest(this);
        }
    }


}
