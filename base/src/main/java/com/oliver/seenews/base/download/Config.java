package com.oliver.seenews.base.download;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.Constant.Download;
import com.oliver.seenews.base.Constant.Http.Request;
import com.oliver.seenews.base.util.FileUtils;

public class Config {

    private int mConcurrentSize;
    private String mCacheDirPath;
    private int mTimeOut;
    private int mRetryCount;

    private Config(Builder builder) {
        mConcurrentSize = builder.mConcurrentSize;
        mCacheDirPath = builder.mCacheDirPath;
        mTimeOut = builder.mTimeOut;
        mRetryCount = builder.mRetryCount;
    }

    public int getConcurrentSize() {
        return mConcurrentSize;
    }

    public String getCacheDirPath() {
        return mCacheDirPath;
    }

    public int getTimeOut() {
        return mTimeOut;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public static class Builder {

        private int mConcurrentSize;
        private String mCacheDirPath;
        private int mTimeOut;
        private int mRetryCount;

        public Builder() {
            mConcurrentSize = Download.DEFAULT_CONCURRENT_SIZE;
            mCacheDirPath = FileUtils.getCacheDirPath();
            mTimeOut = Request.TIME_OUT;
            mRetryCount = Request.RETRY_COUNT;
        }

        public Builder setConcurrentSize(int size) {
            if (size > 0 && size <= Download.MAX_CONCURRENT_SIZE) {
                mConcurrentSize = Download.MAX_CONCURRENT_SIZE;
            }
            return this;
        }

        public Builder setCacheDir(@NonNull String dirPath) {
            mCacheDirPath = dirPath;
            return this;
        }

        public Builder setTimeOut(int timeOut) {
            if (timeOut > 0) {
                mTimeOut = timeOut;
            }
            return this;
        }

        public Builder setRetryCount(int retryCount) {
            if (retryCount > 0) {
                mRetryCount = retryCount;
            }
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}
