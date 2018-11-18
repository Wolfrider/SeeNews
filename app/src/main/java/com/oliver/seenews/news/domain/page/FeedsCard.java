package com.oliver.seenews.news.domain.page;

import java.util.Locale;

import com.alibaba.android.arouter.launcher.ARouter;

import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant;

public class FeedsCard implements INewsPageCard {

    private static final String TAG = "FeedsCard";

    private int mId;
    private String mImageUrl;
    private String mTitle;
    private String mIntro;

    public FeedsCard() {

    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setImageUrl(String url) {
        mImageUrl = url;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setIntro(String intro) {
        mIntro = intro;
    }

    public String getIntro() {
        return mIntro;
    }

    @Override
    public int getType() {
        return CardType.FEEDS;
    }

    @Override
    public void onShow() {
        LogUtils.debug(TAG, "onShow, " + mTitle);
    }

    @Override
    public void onLeft() {
        LogUtils.debug(TAG, "onLeft, " + mTitle);
    }

    public void onClick() {
        LogUtils.debug(TAG, "onClick, " + mTitle);
        ARouter.getInstance().build(Constant.ARouter.DETAIL_PATH)
            .withInt(Constant.ARouter.PARAM_ID, mId)
            .navigation();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{FeedsCard: mId = %d, mImageUrl = %s, mTitle = %s, mIntro = %s}",
            mId, mImageUrl, mTitle, mIntro);
    }
}
