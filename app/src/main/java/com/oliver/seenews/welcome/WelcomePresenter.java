package com.oliver.seenews.welcome;

import java.util.concurrent.TimeUnit;

import com.alibaba.android.arouter.launcher.ARouter;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.ILifecycleListener;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.base.rxjava.BaseObserver;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant;
import com.oliver.seenews.news.domain.News;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WelcomePresenter extends LifecyclePresenter implements WelcomeContract.Presenter {

    private static final String TAG = "WelcomePresenter";

    private WelcomeContract.View mView;

    private static final int MAX_SPLASH_TIME = 1;

    public WelcomePresenter(@NonNull WelcomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public ILifecycleListener getLifecycleListener() {
        return super.getLifecycleListener();
    }

    @Override
    protected void onCreate() {
        LogUtils.debug(TAG, "onCreate");
        mView.showPage();
        Observable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseObserver<Long>() {
                @Override
                public void onComplete() {
                    super.onComplete();
                    gotoHomePage();
                }
        });
    }

    private void gotoHomePage() {
        ARouter.getInstance().build(Constant.ARouter.HOME_PATH).navigation();
        mView.closePage();
    }

}
