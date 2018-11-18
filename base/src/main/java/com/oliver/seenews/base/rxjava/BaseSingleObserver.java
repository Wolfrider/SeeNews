package com.oliver.seenews.base.rxjava;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class BaseSingleObserver<T> implements SingleObserver<T> {
    private Disposable mDisposable;

    @Override
    public final void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onSuccess(T t) {
        if (null != mDisposable) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (null != mDisposable) {
            mDisposable.dispose();
        }
    }
}
