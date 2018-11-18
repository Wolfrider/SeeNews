package com.oliver.seenews.base.download;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.oliver.seenews.base.Constant.Download.Priority;
import com.oliver.seenews.base.Constant.Download.Status;
import com.oliver.seenews.base.util.EncipherUtils;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;

public class Task implements Comparable<Task> {

    private static final String TAG = "Task";

    private String mUrl;
    private String mCacheDirPath;
    private String mFileName;
    private String mMd5;
    private int mPriority;
    private long mCreateTime;
    private ITaskListener mListener;
    private volatile AtomicInteger mStatus;

    private Task(Builder builder) {
        mUrl = builder.mUrl;
        mCacheDirPath = builder.mCacheDirPath;
        mFileName = builder.mFileName;
        mMd5 = builder.mMd5;
        mPriority = builder.mPriority;
        mListener = builder.mListener;
        mStatus = new AtomicInteger(Status.NONE);
        mCreateTime = System.currentTimeMillis();
    }

    public String getUrl() {
        return mUrl;
    }

    String getCacheDirPath() {
        return mCacheDirPath;
    }

    public String getFilePath() {
        return FileUtils.joinPath(mCacheDirPath, mFileName);
    }

    public String getMd5() {
        return mMd5;
    }

    public boolean cancel() {
        return mStatus.compareAndSet(Status.NONE, Status.CANCEL);
    }

    void setCacheDirPath(@NonNull String cacheDirPath) {
        mCacheDirPath = cacheDirPath;
    }

    int getStatus() {
        return mStatus.get();
    }

    boolean hitCache() {
        String filePath = FileUtils.joinPath(mCacheDirPath, mFileName);
        if (FileUtils.exists(filePath)) {
            if (!TextUtils.isEmpty(mMd5)) {
                return mMd5.equalsIgnoreCase(EncipherUtils.md5FromFile(filePath));
            }
            return true;
        }
        return false;
    }

    void onStart() {
        updateStatus(Status.START);
        LogUtils.debug(TAG, "onStart");
        if (null != mListener) {
            mListener.onStart();
        }
    }

    void onCancel() {
        updateStatus(Status.CANCEL);
        LogUtils.debug(TAG, "onCancel");
        if (null != mListener) {
            mListener.onCancel();
        }
    }

    void onFinish(boolean fromCache) {
        updateStatus(Status.FINISH);
        long time = System.currentTimeMillis() - mCreateTime;
        LogUtils.debug(TAG, String.format(Locale.US, "onFinish, fromCache = %s, time = %d", fromCache, time));
        if (null != mListener) {
            mListener.onFinish(fromCache, time, getFilePath());
        }
    }

    void onFail(int errorCode) {
        updateStatus(Status.FAIL);
        LogUtils.warn(TAG, String.format(Locale.US, "onFail, errorCode = %d", errorCode));
        if (null != mListener) {
            mListener.onFail(errorCode);
        }
    }

    void onProgress(int current, int total) {
        LogUtils.info(TAG, String.format(Locale.US, "onProgress, current = %d, total = %d", current, total));
        if (null != mListener) {
            mListener.onProgress(current, total);
        }
    }

    private void updateStatus(int status) {
        mStatus.getAndSet(status);
    }

    @Override
    public int compareTo(@NonNull Task o) {
        if (this == o) {
            return 0;
        }
        int result = Integer.compare(mPriority, o.mPriority);
        if (0 == result) {
            result = -1 * Long.compare(mCreateTime, o.mCreateTime);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Task) {
            Task other = (Task)obj;
            return mPriority == other.mPriority
                && mUrl.equals(other.mUrl)
                && mFileName.equals(other.mFileName);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{Task: mUrl = %s, mCacheDirPath = %s, mFileName = %s, "
                + "mMd5 = %s, mPriority = %d, mStatus = %d}",
            mUrl, mCacheDirPath, mFileName, mMd5, mPriority, mStatus.get());
    }

    public static class Builder {

        private String mUrl;
        private String mCacheDirPath;
        private String mFileName;
        private String mMd5;
        private int mPriority;
        private ITaskListener mListener;
        //TODO:http相关信息：像header，post，form等

        public Builder() {
            mPriority = Priority.NORMAL;
        }

        public Builder setUrl(@NonNull String url) {
            mUrl = url;
            return this;
        }

        public Builder setCacheDirPath(@NonNull String cacheDirPath) {
            mCacheDirPath = cacheDirPath;
            return this;
        }

        public Builder setFileName(@NonNull String fileName) {
            mFileName = fileName;
            return this;
        }

        public Builder setMd5(@NonNull String md5) {
            mMd5 = md5;
            return this;
        }

        public Builder setPriority(int priority) {
            mPriority = priority;
            return this;
        }

        public Builder setListener(@NonNull ITaskListener listener) {
            mListener = listener;
            return this;
        }

        public Task build() {
            return new Task(this);
        }

    }
}
