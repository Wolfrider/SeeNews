package com.oliver.seenews.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.arch.BaseFragment;
import com.oliver.seenews.base.util.LogUtils;

public class NewsFragment extends BaseFragment<NewsPresenter> {

    private static final String TAG = "NewsFragment";

    public NewsFragment() {

    }

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        mPresenter = new NewsPresenter(new NewsView(this));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.debug(TAG, "onCreateView");
        return inflater.inflate(R.layout.news_fragment, null);
    }
}
