package com.oliver.seenews.news.detail;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.detail.DetailPage;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailPagePresenter extends LifecyclePresenter implements DetailPageContract.Presenter {

    private static final String TAG = "DetailPagePresenter";

    private DetailPageContract.View mView;
    private DetailPage mDetailPage;

    public DetailPagePresenter(@NonNull DetailPageContract.View view, int id) {
        mView = view;
        mView.setPresenter(this);
        mDetailPage = new DetailPage(id);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mView.init();
        mDetailPage.refresh().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseCompletableObserver() {
                @Override
                public void onComplete() {
                    super.onComplete();
                    mView.showPage(mDetailPage.getDetail());
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
    }
}
