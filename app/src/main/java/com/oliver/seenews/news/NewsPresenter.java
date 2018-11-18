package com.oliver.seenews.news;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.arch.ServiceLocator;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.News;
import io.reactivex.android.schedulers.AndroidSchedulers;
import org.greenrobot.eventbus.EventBus;

public class NewsPresenter extends LifecyclePresenter implements NewsContract.Presenter {

    private static final String TAG = "NewsPresenter";

    private NewsContract.View mView;
    private News mNews;

    public NewsPresenter(@NonNull NewsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mView.showLoadingPage();
        initNews();
    }

    private void initNews() {
        mNews = new News();
        ServiceLocator.getInstance().put(mNews, News.class);
        mNews.init().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseCompletableObserver() {
                @Override
                public void onComplete() {
                    super.onComplete();
                    //LogUtils.debug(TAG, mNews.getTitleList().toString());
                    mView.showPage(mNews.getNewsPages());
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
    }

}
