package com.oliver.seenews.magazine.detail;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.arch.ServiceLocator;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.magazine.domain.MagazineDetail;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MagazineDetailPresenter extends LifecyclePresenter implements MagazineDetailContract.Presenter {

    private MagazineDetailContract.View mView;
    private MagazineDetail mDetail;

    public MagazineDetailPresenter(@NonNull MagazineDetailContract.View view, int id) {
        mView = view;
        mDetail = new MagazineDetail(id);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mView.init();
        mDetail.refresh().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseCompletableObserver() {
                @Override
                public void onComplete() {
                    super.onComplete();
                    mView.showPage(mDetail.getDetail());
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
    }


}
