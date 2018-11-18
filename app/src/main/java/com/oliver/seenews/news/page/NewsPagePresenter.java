package com.oliver.seenews.news.page;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.arch.ServiceLocator;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.base.rxjava.BaseSingleObserver;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.News;
import com.oliver.seenews.news.domain.page.INewsPageCard;
import com.oliver.seenews.news.domain.page.NewsPage;
import com.oliver.seenews.news.domain.page.FeedsCard;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class NewsPagePresenter extends LifecyclePresenter implements NewsPageContract.Presenter {

    private static final String TAG = "NewsPagePresenter";

    private NewsPageContract.View mView;
    private NewsPage mNewsPage;
    private List<INewsPageCard> mCards;

    public NewsPagePresenter(@NonNull NewsPageContract.View view, int id) {
        mView = view;
        mView.setPresenter(this);
        News news = (News)ServiceLocator.getInstance().get(News.class);
        mNewsPage = news.find(id);
    }

    @Override
    public void init() {
        mView.init();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    @Override
    public void onLoad() {
        mNewsPage.load()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSingleObserver<List<INewsPageCard>>() {
                @Override
                public void onSuccess(List<INewsPageCard> cards) {
                    super.onSuccess(cards);
                    mView.showMore(cards);
                }
            });
    }

    private void refresh() {
        mNewsPage.refresh().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSingleObserver<List<INewsPageCard>>() {
                @Override
                public void onSuccess(List<INewsPageCard> newsPageCards) {
                    super.onSuccess(newsPageCards);
                    LogUtils.debug(TAG, "refresh onComplete " + mNewsPage.getTitle().getName());
                    mCards = newsPageCards;
                    mView.showPage(mCards);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    LogUtils.debug(TAG, "refresh onError " + mNewsPage.getTitle().getName());
                }
            });
    }

    private void start() {
        mView.start();
    }

    private void stop() {
        mView.stop();
    }

}
