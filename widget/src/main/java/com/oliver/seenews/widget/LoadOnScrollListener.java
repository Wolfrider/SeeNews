package com.oliver.seenews.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LoadOnScrollListener extends RecyclerView.OnScrollListener {

    public interface OnLoadListener {
        void onLoad();
    }

    private LinearLayoutManager mLinearLayoutManager;
    private OnLoadListener mListener;
    private boolean mLoading;

    public LoadOnScrollListener(LinearLayoutManager layoutManager) {
        mLinearLayoutManager = layoutManager;
    }

    public void setListener(@NonNull OnLoadListener listener) {
        mListener = listener;
    }

    public void setLoadFinish() {
        mLoading = false;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mLoading) {
            return;
        }
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        int totalCount = mLinearLayoutManager.getItemCount();
        if (totalCount - 1 <= lastPosition) {
            if (null != mListener) {
                mLoading = true;
                mListener.onLoad();
            }
        }
    }
}

