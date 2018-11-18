package com.oliver.seenews.base.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable mDisposable;

    @Override
    public final void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (null != mDisposable) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }

    @Override
    public void onComplete() {
        if (null != mDisposable) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }
}
