package com.oliver.seenews.magazine.domain;

import java.util.Locale;

import com.alibaba.android.arouter.launcher.ARouter;

import com.oliver.seenews.base.dmm.document.Magazine;
import com.oliver.seenews.base.download.Downloader;
import com.oliver.seenews.base.download.ITaskListener;
import com.oliver.seenews.base.download.Task;
import com.oliver.seenews.base.util.EncipherUtils;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MagazineDetailItem {

    private static final String TAG = "MagazineDetailItem";

    private int mId;
    private String mCoverImageUrl;
    private String mTitle;
    private String mPublishDate;
    private String mDmmUrl;
    private String mSize;
    private int mScorePoint;
    private String mDesc;
    private String mLocalDmmPath;
    private boolean mDownloading;

    public MagazineDetailItem() {
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setCoverImageUrl(String url) {
        mCoverImageUrl = url;
    }

    public String getCoverImageUrl() {
        return mCoverImageUrl;
    }

    public void setTitle(String title) {
        int beginIndex = title.indexOf('-') + 1;
        mTitle = title.substring(beginIndex);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setPublishDate(String date) {
        mPublishDate = String.format(Locale.CHINA, "日期： %s", date.substring(0, date.indexOf(' ')));
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public void setDmmUrl(String url) {
        mDmmUrl = url;
        mLocalDmmPath = FileUtils.joinPath(Constant.LOCAL_DMM_DIR, EncipherUtils.md5(mDmmUrl));
        LogUtils.debug(TAG, "mLocalDmmPath, " + mLocalDmmPath);
    }

    public String getDmmUrl() {
        return mDmmUrl;
    }

    public void setSize(String size) {
        mSize = String.format(Locale.CHINA, "大小： %s", size);
    }

    public String getSize() {
        return mSize;
    }

    public void setScorePoint(int point) {
        mScorePoint = point;
    }

    public int getScorePoint() {
        return mScorePoint;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getLocalDmmPath() {
        return mLocalDmmPath;
    }

    public boolean hasDownloaded() {
        return FileUtils.exists(mLocalDmmPath);
    }

    public boolean isDownloading() {
        return mDownloading;
    }

    public Observable<Integer> onDownload() {
        if (mDownloading) {
            LogUtils.debug(TAG, "downloading...");
            return Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    emitter.onError(new IllegalStateException());
                }
            });
        }
        LogUtils.debug(TAG, "onDownload, " + mDmmUrl);
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                Task.Builder builder = new Task.Builder();
                builder.setUrl(mDmmUrl)
                    .setFileName(EncipherUtils.md5(mDmmUrl))
                    .setListener(new ITaskListener() {
                        @Override
                        public void onStart() {
                            mDownloading = true;
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onFail(int errorCode) {
                            emitter.onError(new Exception());
                        }

                        @Override
                        public void onProgress(int current, int total) {
                            int progress = (int)(current * 100.0F / total);
                            LogUtils.debug(TAG, "download... " + progress);
                            emitter.onNext(progress);
                        }

                        @Override
                        public void onFinish(boolean fromCache, long time, String filePath) {
                            FileUtils.mkdirs(mLocalDmmPath);
                            FileUtils.unzip(filePath, mLocalDmmPath);
                            LogUtils.debug(TAG, "download finish, unzip dmm file to " + mLocalDmmPath);
                            emitter.onComplete();
                            mDownloading = false;
                        }
                    });
                Downloader.getInstance().addTask(builder.build());
            }
        });
    }

    public void onRead() {
        LogUtils.debug(TAG, "onRead, " + mLocalDmmPath);
        ARouter.getInstance().build(Constant.ARouter.MAGAZINE_READ_PATH)
            .withString(Constant.ARouter.PARAM_PATH, mLocalDmmPath)
            .withString(Constant.ARouter.PARAM_TITLE, getTitle())
            .navigation();
    }

}
