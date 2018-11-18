package com.oliver.seenews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class DownloadButton extends AppCompatButton {

    private Drawable mCompleteDrawable;
    private float mCompleteProgress;

    public DownloadButton(@NonNull Context context) {
        this(context, null);
    }

    public DownloadButton(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        mCompleteDrawable = getContext().getDrawable(R.drawable.download_complete);
        initAttrs(attributeSet);
        setBackground(getContext().getDrawable(R.drawable.download_incomplete));
    }

    private void initAttrs(@Nullable AttributeSet attributeSet) {
        if (null != attributeSet) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.DownloadButton);
            Drawable drawable = typedArray.getDrawable(R.styleable.DownloadButton_completeDrawable);
            if (null != drawable) {
                mCompleteDrawable = drawable;
            }
            float progress = typedArray.getFloat(R.styleable.DownloadButton_completeProgress, 0);
            if (progress >= 0 &&  mCompleteProgress <= 1.0F) {
                mCompleteProgress = progress;
            }
            typedArray.recycle();
        }

    }

    public void setCompleteDrawable(@DrawableRes int drawableRes) {
        mCompleteDrawable = getContext().getDrawable(drawableRes);
        invalidate();
    }

    public void setCompleteProgress(float progress) {
        if (progress >= 0 && progress <= 1.0F) {
            mCompleteProgress = progress;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCompleteProgress > 0) {
            mCompleteDrawable.setBounds(0, 0, (int)(getMeasuredWidth() * mCompleteProgress), getMeasuredHeight());
            mCompleteDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }
}
