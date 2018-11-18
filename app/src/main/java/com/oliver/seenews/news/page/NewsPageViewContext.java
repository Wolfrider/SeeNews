package com.oliver.seenews.news.page;

import android.content.Context;
import android.support.annotation.NonNull;

public class NewsPageViewContext {

    private Context mContext;
    private boolean mIsShowing;

    public NewsPageViewContext(@NonNull Context context) {
        mContext = context;
        mIsShowing = false;
    }

    public void setShowing(boolean showing) {
        if (showing != mIsShowing) {
            mIsShowing = true;
        }
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public Context getContext() {
        return mContext;
    }
}
