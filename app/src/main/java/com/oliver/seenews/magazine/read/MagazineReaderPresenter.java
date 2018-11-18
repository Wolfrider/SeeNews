package com.oliver.seenews.magazine.read;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.arch.LifecyclePresenter;
import com.oliver.seenews.base.dmm.DMMReader;
import com.oliver.seenews.base.dmm.document.Magazine;
import com.oliver.seenews.base.rxjava.BaseSingleObserver;
import com.oliver.seenews.base.util.LogUtils;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MagazineReaderPresenter extends LifecyclePresenter implements MagazineReaderContract.Presenter {

    private static final String TAG = "MagazineReaderPresenter";

    private MagazineReaderContract.View mView;
    private String mLocalDmmPath;

    public MagazineReaderPresenter(@NonNull MagazineReaderContract.View view, @NonNull String dmmPath) {
        mView = view;
        mView.setPresenter(this);
        mLocalDmmPath = dmmPath;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        LogUtils.debug(TAG, "init view");
        mView.init();
        loadMagazine().observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseSingleObserver<Magazine>(){
                @Override
                public void onSuccess(Magazine magazine) {
                    super.onSuccess(magazine);
                    LogUtils.debug(TAG, "read finish");
                    mView.load(mLocalDmmPath, magazine);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    LogUtils.debug(TAG, "read finish");
                }
            });
    }

    private Single<Magazine> loadMagazine() {
        return Single.create(new SingleOnSubscribe<Magazine>() {
            @Override
            public void subscribe(SingleEmitter<Magazine> emitter) throws Exception {
                Magazine magazine = new DMMReader().read(mLocalDmmPath);
                if (null != magazine) {
                    emitter.onSuccess(magazine);
                } else {
                    emitter.onError(new Exception());
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
