package com.oliver.seenews.magazine;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.base.widget.LoadingDialog;
import com.oliver.seenews.widget.LoadOnScrollListener;
import com.oliver.seenews.widget.LoadOnScrollListener.OnLoadListener;
import com.oliver.seenews.magazine.MagazineContract.Presenter;
import com.oliver.seenews.magazine.domain.MagazineItem;

public class MagazineView implements MagazineContract.View {

    private static final String TAG = "MagazineView";

    private MagazineContract.Presenter mPresenter;
    private Fragment mFragment;
    private LoadingDialog mLoadingDialog;
    private View mView;
    private RecyclerView mRecyclerView;
    private MagazineAdapter mMagazineAdapter;
    private LoadOnScrollListener mScrollListener;

    public MagazineView(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        LogUtils.debug(TAG, "init");
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(mFragment.getChildFragmentManager(), TAG);

        mView = mFragment.getView();
        mRecyclerView = mView.findViewById(R.id.magazine_content);
        mMagazineAdapter = new MagazineAdapter(mFragment.getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(mFragment.getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMagazineAdapter);
        mScrollListener = new LoadOnScrollListener(layoutManager);
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
    public void showPage(@NonNull List<MagazineItem> magazineItems) {
        LogUtils.debug(TAG, "showPage, " + magazineItems.size());
        mMagazineAdapter.add(magazineItems);
        mLoadingDialog.dismiss();
    }

    @Override
    public void add(@NonNull List<MagazineItem> magazineItems) {
        LogUtils.debug(TAG, "add, " + magazineItems.size());
        mMagazineAdapter.add(magazineItems);
        mScrollListener.setLoadFinish();
    }
}
