package com.oliver.seenews.magazine.detail.comments;

import java.util.LinkedList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.detail.comments.MagazineDetailCommentsContract.Presenter;
import com.oliver.seenews.magazine.domain.MagazineCommentItem;
import com.oliver.seenews.widget.LoadOnScrollListener;
import com.oliver.seenews.widget.LoadOnScrollListener.OnLoadListener;

public class MagazineDetailCommentsView implements MagazineDetailCommentsContract.View {

    private static final String TAG = "MagazineDetailCommentsView";

    private MagazineDetailCommentsContract.Presenter mPresenter;
    private List<MagazineCommentItem> mComments;

    private Fragment mFragment;
    private View mView;
    private RecyclerView mRecyclerView;
    private LoadOnScrollListener mLoadOnScrollListener;
    private MagazineCommentsAdapter mAdapter;



    public MagazineDetailCommentsView(@NonNull Fragment fragment) {
        mFragment = fragment;
        mComments = new LinkedList<>();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        LogUtils.debug(TAG, "init");
        mView = mFragment.getView();
        mRecyclerView = mView.findViewById(R.id.magazine_detail_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mFragment.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLoadOnScrollListener = new LoadOnScrollListener(linearLayoutManager);
        mLoadOnScrollListener.setListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                mPresenter.onLoad();
            }
        });
        mRecyclerView.addOnScrollListener(mLoadOnScrollListener);
        mAdapter = new MagazineCommentsAdapter(mFragment.getContext(), mComments);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragment.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showMore(@NonNull List<MagazineCommentItem> commentItems) {
        LogUtils.debug(TAG, "showMore");
        mLoadOnScrollListener.setLoadFinish();
        mAdapter.add(commentItems);
    }

}
