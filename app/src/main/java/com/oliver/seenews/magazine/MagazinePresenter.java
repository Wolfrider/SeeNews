package com.oliver.seenews.magazine;

import java.util.List;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.rxjava.BaseCompletableObserver;
import com.oliver.seenews.base.rxjava.BaseSingleObserver;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.domain.MagazineItem;
import com.oliver.seenews.magazine.domain.Magazines;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MagazinePresenter extends LifecyclePresenter implements MagazineContract.Presenter {

    private static final String TAG = "MagazinePresenter";

    private MagazineContract.View mView;
    private Magazines mMagazines;

    public MagazinePresenter(@NonNull MagazineContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mMagazines = new Magazines();
    }

    @Override
    public void init() {
        mView.init();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initMagazines();
    }

    @Override
    public void onLoad() {
        mMagazines.load().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSingleObserver<List<MagazineItem>>() {
                @Override
                public void onSuccess(List<MagazineItem> magazineItems) {
                    super.onSuccess(magazineItems);
                    mView.add(magazineItems);
                }
            });
    }

    private void initMagazines() {
        mMagazines.refresh().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseCompletableObserver() {
                @Override
                public void onComplete() {
                    super.onComplete();
                    mView.showPage(mMagazines.get());
                }
            });
    }
}
