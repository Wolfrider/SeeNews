package com.oliver.seenews.base.download;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.oliver.seenews.base.Constant.Download;
import com.oliver.seenews.base.Constant.Download.ErrorCode;
import com.oliver.seenews.base.Constant.Download.Status;
import com.oliver.seenews.base.Constant.Http.Method;
import com.oliver.seenews.base.http.HttpCommunication;
import com.oliver.seenews.base.http.HttpDownloadRequest;
import com.oliver.seenews.base.http.HttpDownloadRequest.Builder;
import com.oliver.seenews.base.http.IHttpDownloadListener;
import com.oliver.seenews.base.util.EncipherUtils;
import com.oliver.seenews.base.util.FileUtils;

class HttpDownloadJob {

    private Config mConfig;
    private Task mTask;
    private String mTmpFilePath;

    HttpDownloadJob() {
    }

    void exec(@NonNull Config config, @NonNull Task task) {
        mConfig = config;
        mTask = task;

        if (task.getStatus() == Status.CANCEL) {
            task.onCancel();
        } else {
            if (TextUtils.isEmpty(task.getCacheDirPath())) {
                task.setCacheDirPath(mConfig.getCacheDirPath());
            }
            FileUtils.mkdirs(task.getCacheDirPath());
            task.onStart();
            if (task.hitCache()) {
                task.onFinish(true);
            } else {
                mTmpFilePath = task.getFilePath() + "_tmp";
                FileUtils.delete(task.getFilePath());
                FileUtils.delete(mTmpFilePath);
                HttpCommunication.getInstance().sendDownloadRequest(createHttpRequest(),
                    new HttpDownloadListener());
            }
        }
    }

    private HttpDownloadRequest createHttpRequest() {
        HttpDownloadRequest.Builder builder = new Builder();
        builder.setFilePath(mTmpFilePath)
            .setUrl(mTask.getUrl())
            .setTimeOut(mConfig.getTimeOut())
            .setRetryCount(mConfig.getRetryCount())
            .setMethod(Method.GET);
        return builder.build();
    }

    private class HttpDownloadListener implements IHttpDownloadListener {

        private int mTotalSize;
        private int mCurrentSize;

        HttpDownloadListener() {
        }

        @Override
        public void onStart(int totalSize) {
            mTotalSize = totalSize;
            mCurrentSize = 0;
        }

        @Override
        public void onProgress(int growSize) {
            mCurrentSize += growSize;
            mTask.onProgress(mCurrentSize, mTotalSize);
        }

        @Override
        public void onFail(int errorCode) {
            mTask.onFail(errorCode);
        }

        @Override
        public void onFinish() {
            if (mTotalSize > 0 && !TextUtils.isEmpty(mTask.getMd5())) {
                if (!mTask.getMd5().equalsIgnoreCase(EncipherUtils.md5FromFile(mTmpFilePath))) {
                    mTask.onFail(ErrorCode.INVALID_MD5);
                    FileUtils.delete(mTmpFilePath);
                    return;
                }
            }
            FileUtils.rename(mTmpFilePath, mTask.getFilePath());
            mTask.onFinish(false);
        }
    }

}
