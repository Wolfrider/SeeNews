package com.oliver.seenews.magazine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.arch.BaseFragment;
import com.oliver.seenews.base.util.LogUtils;

public class MagazineFragment extends BaseFragment<MagazinePresenter> {

    private static final String TAG = "MagazineFragment";

    public MagazineFragment() {

    }

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        mPresenter = new MagazinePresenter(new MagazineView(this));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.magazine_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter.init();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.debug(TAG, "setUserVisibleHint, " + isVisibleToUser);
    }
}
