package com.oliver.seenews.base.arch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<TPresenter extends LifecyclePresenter> extends AppCompatActivity {

    private LifecycleAdapter mLifecycleAdapter;
    protected TPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateStart(savedInstanceState);
        onCreateFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyStart();
        onDestroyFinish();
    }

    protected void onCreateFinish() {
        mLifecycleAdapter = new LifecycleAdapter(mPresenter.getLifecycleListener());
        getLifecycle().addObserver(mLifecycleAdapter);
    }

    protected void onDestroyFinish() {
        getLifecycle().removeObserver(mLifecycleAdapter);
        mLifecycleAdapter = null;
    }

    protected abstract void onCreateStart(@Nullable Bundle savedInstanceState);

    protected void onDestroyStart() {

    }

}
