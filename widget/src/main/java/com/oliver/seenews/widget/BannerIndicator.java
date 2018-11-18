package com.oliver.seenews.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.oliver.seenews.base.util.LogUtils;

public class BannerIndicator extends LinearLayout {

    private static final String TAG = "BannerIndicator";

    private int mCount = -1;
    private int mCurrentIndex = -1;

    public BannerIndicator(@NonNull Context context) {
        super(context);
    }

    public BannerIndicator(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setCount(int count) {
        if (count <= 0) {
            return;
        }
        LogUtils.debug(TAG, "setCount count = " + count);
        setOrientation(HORIZONTAL);
        mCount = count;
        removeAllViews();
        int width = getResources().getDimensionPixelSize(R.dimen.banner_indicator_width);
        for (int i = 0; i < mCount; ++i) {
            AppCompatImageView imageView = new AppCompatImageView(getContext());
            imageView.setImageResource(R.drawable.banner_indicator_unselected);
            imageView.setScaleType(ScaleType.CENTER);
            addView(imageView, new ViewGroup.LayoutParams(width, width));
        }
    }

    public int getCount() {
        return mCount;
    }

    public void setCurrent(int index) {
        if (index >= 0 && index < mCount && mCurrentIndex != index) {
            if (mCurrentIndex > -1) {
                AppCompatImageView imageView = (AppCompatImageView)getChildAt(mCurrentIndex);
                imageView.setImageResource(R.drawable.banner_indicator_unselected);
            }
            mCurrentIndex = index;
            AppCompatImageView imageView = (AppCompatImageView)getChildAt(mCurrentIndex);
            imageView.setImageResource(R.drawable.banner_indicator_selected);
        }
    }

    public int getCurrent() {
        return mCurrentIndex;
    }
}

