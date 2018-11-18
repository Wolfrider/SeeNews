package com.oliver.seenews.home;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;

public class HomePresenter extends LifecyclePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private HomeContract.View mHomeView;

    public HomePresenter(@NonNull HomeContract.View homeView) {
        mHomeView = homeView;
        mHomeView.setPresenter(this);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mHomeView.showPage();
    }

    @Override
    public void onClear() {
        long size = FileUtils.getSize(FileUtils.getCacheDirPath());
        size += FileUtils.getSize(FileUtils.getFilesDirPath());
        LogUtils.debug(TAG, "file size = " + FileUtils.toMB(size));
        FileUtils.delete(FileUtils.getCacheDirPath());
        FileUtils.delete(FileUtils.getFilesDirPath());
        mHomeView.showClearTips(FileUtils.toMB(size));
    }
}
