package com.oliver.seenews.base.dmm;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;
import com.bumptech.glide.Glide;
import com.oliver.seenews.base.dmm.document.Body;
import com.oliver.seenews.base.dmm.document.Focus;
import com.oliver.seenews.base.dmm.document.Img;
import com.oliver.seenews.base.dmm.document.Page;
import com.oliver.seenews.base.dmm.document.Text;
import com.oliver.seenews.base.util.DeviceUtils;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.base.util.NumUtils;

public class DMMPageView extends FrameLayout {

    private static final String TAG = "DMMPageView";

    public interface OnFocusClickListener {

        void onClick(String dest);
    }

    private String mLocalDirPath;
    private Page mPage;
    private int mWidth;
    private int mHeight;
    private OnFocusClickListener mOnFocusClickListener;

    public DMMPageView(@NonNull Context context) {
        super(context);
    }

    public DMMPageView(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setData(@NonNull String localDirPath, @NonNull Page page, int width, int height) {
        mLocalDirPath = localDirPath;
        mPage = page;
        mWidth = width;
        mHeight = height;
        init();
    }

    public void setOnFocusClickListener(@NonNull OnFocusClickListener listener) {
        mOnFocusClickListener = listener;
    }

    private void init() {
        for (Body body: mPage) {
            if (body instanceof Img) {
                addImage((Img)body);
            } else if (body instanceof Text) {
                addText((Text)body);
            } else if (body instanceof Focus) {
                addFocus((Focus)body);
            }
        }
    }

    private void addImage(Img img) {
        AppCompatImageView imageView = new AppCompatImageView(getContext());
        imageView.setScaleType(ScaleType.FIT_XY);
        Glide.with(this).load(FileUtils.joinPath(mLocalDirPath, img.getSrc())).into(imageView);
        addView(imageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private void addText(Text text) {
        AppCompatTextView textView = new AppCompatTextView(getContext());
        textView.setTextSize(text.getFontSize());
        textView.setTextColor(text.getFontColor());
        textView.setText(text.getContent());
        MarginLayoutParams layoutParams = new MarginLayoutParams(getScaledValue(text.getWidth()), getScaledValue(text.getHeight()));
        layoutParams.setMargins(getScaledValue(text.getX()), getScaledValue(text.getY()), 0, 0);
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(textView);
        addView(scrollView, layoutParams);
    }

    private void addFocus(final Focus focus) {
        View view = new View(getContext());
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnFocusClickListener) {
                    mOnFocusClickListener.onClick(focus.getDest());
                }
            }
        });
        MarginLayoutParams layoutParams = new MarginLayoutParams(getScaledValue(focus.getWidth()), getScaledValue(focus.getHeight()));
        layoutParams.setMargins(getScaledValue(focus.getX()), getScaledValue(focus.getY()), 0, 0);
        //view.setBackgroundColor(Color.argb(66, 255, 0, 0));
        addView(view, layoutParams);
    }

    private int getScaledValue(int value) {
        return NumUtils.toInt(value * DeviceUtils.getScreenWidth() * 1.0 / mWidth);
    }

}
