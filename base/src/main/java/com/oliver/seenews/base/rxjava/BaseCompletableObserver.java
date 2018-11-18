package com.oliver.seenews.base.rxjava;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class BaseCompletableObserver implements CompletableObserver {

    private Disposable mDisposable;

    @Override
    public final void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onComplete() {
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
