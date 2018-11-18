package com.oliver.seenews.base.arch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.base.util.LogUtils;

public abstract class BaseFragment<TPresenter extends LifecyclePresenter> extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private LifecycleAdapter mLifecycleAdapter;
    protected TPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateStart(getArguments());
        onCreateFinish();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyStart();
        onDestroyFinish();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.debug(TAG, "setUserVisibleHint, " + isVisibleToUser);
        if (isVisibleToUser) {
            if (null != mLifecycleAdapter) {
                mLifecycleAdapter.onStart();
            }
        } else {
            if (null != mLifecycleAdapter) {
                mLifecycleAdapter.onStop();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.debug(TAG, "onHiddenChanged, " + hidden);
        if (hidden) {
            if (null != mLifecycleAdapter) {
                mLifecycleAdapter.onStop();
            }
        } else {
            if (null != mLifecycleAdapter) {
                mLifecycleAdapter.onStart();
            }
        }
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

    protected void onDestroyStart() {}

}
