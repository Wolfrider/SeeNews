package com.oliver.seenews.base;

import com.alibaba.android.arouter.launcher.ARouter;

import android.app.Application;
import android.webkit.WebSettings;
import com.oliver.seenews.base.download.Config;
import com.oliver.seenews.base.download.Config.Builder;
import com.oliver.seenews.base.download.Downloader;
import com.oliver.seenews.base.http.HttpCommunication;
import com.oliver.seenews.base.util.AppUtils;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.base.util.NetworkUtils;

public class SeeNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        AppUtils.init(this);
        if (AppUtils.isDebug()) {
            LogUtils.setLogLevel(LogUtils.VERBOSE);
            ARouter.openLog();
            ARouter.openDebug();
            HttpCommunication.getInstance().openLog();
        } else {
            LogUtils.setLogLevel(LogUtils.ERROR);
        }
        ARouter.init(this);
        NetworkUtils.init();
        HttpCommunication.getInstance().setUserAgent(WebSettings.getDefaultUserAgent(this));
        String cacheDirPath = FileUtils.joinPath(FileUtils.getCacheDirPath(), Constant.Download.CACHE_DIR_NAME);
        Config config = new Builder().setCacheDir(cacheDirPath).build();
        Downloader.getInstance().init(config);
    }
}
