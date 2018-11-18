package com.oliver.seenews.news.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.arch.BaseFragment;
import com.oliver.seenews.base.util.LogUtils;

public class NewsPageFragment extends BaseFragment<NewsPagePresenter> {

    private static final String TAG = "NewsPageFragment";

    private static final String KEY_ID = "id";

    private int mId;

    public static NewsPageFragment createInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        NewsPageFragment fragment = new NewsPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onCreateStart(@Nullable Bundle savedInstanceState) {
        LogUtils.debug(TAG, "onCreateStart " + savedInstanceState);
        if (null != savedInstanceState) {
            mId = savedInstanceState.getInt(KEY_ID);
            mPresenter = new NewsPagePresenter(new NewsPageView(this, mId), mId);
        }
    }

    @Override
    protected void onDestroyStart() {
        LogUtils.debug(TAG, "onDestroyStart, " + mId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.debug(TAG, "onCreateView " + mPresenter);
        return inflater.inflate(R.layout.news_page_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.debug(TAG, "onViewCreated "  + mPresenter);
        mPresenter.init();
        super.onViewCreated(view, savedInstanceState);
    }
}
