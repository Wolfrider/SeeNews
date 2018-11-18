package com.oliver.seenews.news.page;

import java.util.LinkedList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.AppUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.widget.LoadOnScrollListener;
import com.oliver.seenews.widget.LoadOnScrollListener.OnLoadListener;
import com.oliver.seenews.news.domain.page.INewsPageCard;
import com.oliver.seenews.news.page.NewsPageContract.Presenter;

public class NewsPageView implements NewsPageContract.View {

    private static final String TAG = "NewsPageView";

    private int mId;
    private NewsPageContract.Presenter mPresenter;
    private Fragment mFragment;
    private View mView;
    private NewsPageViewContext mViewContext;

    private RecyclerView mRecyclerView;
    private NewsPageAdapter mNewsPageAdapter;
    private LoadOnScrollListener mScrollListener;

    public NewsPageView(@NonNull Fragment fragment, int id) {
        mFragment = fragment;
        mId = id;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        LogUtils.debug(TAG, "init, " + mId);
        mView = mFragment.getView();
        mViewContext = new NewsPageViewContext(AppUtils.getApp());
        mNewsPageAdapter = new NewsPageAdapter(mViewContext, new LinkedList<INewsPageCard>());
        mRecyclerView = mView.findViewById(R.id.news_page_content);
        mRecyclerView.setAdapter(mNewsPageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mViewContext.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mViewContext.getContext(), DividerItemDecoration.VERTICAL));
        mScrollListener = new LoadOnScrollListener(linearLayoutManager);
        mScrollListener.setListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                LogUtils.debug(TAG, "onLoad");
                mPresenter.onLoad();
            }
        });
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    public void showPage(@NonNull List<INewsPageCard> newsPageCards) {
        LogUtils.debug(TAG, "showPage");
        mNewsPageAdapter.add(newsPageCards);
    }

    @Override
    public void showMore(@NonNull List<INewsPageCard> cards) {
        LogUtils.debug(TAG, "showMore");
        mScrollListener.setLoadFinish();
        mNewsPageAdapter.add(cards);
    }

    @Override
    public void start() {
        LogUtils.debug(TAG, "start, " + mId);
        mViewContext.setShowing(true);
    }

    @Override
    public void stop() {
        LogUtils.debug(TAG, "stop, " + mId);
        mViewContext.setShowing(false);
    }

}
