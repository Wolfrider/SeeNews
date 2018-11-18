package com.oliver.seenews.magazine.domain;

import java.util.Locale;

import com.alibaba.android.arouter.launcher.ARouter;

import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.common.Constant;

public class MagazineItem {

    private static final String TAG = "MagazineItem";

    private int mId;
    private String mCoverImgUrl;
    private String mTitle;
    private String mPublishTime;
    private int mVol;

    public MagazineItem() {

    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        mCoverImgUrl = coverImgUrl;
    }

    public String getCoverImgUrl() {
        return mCoverImgUrl;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setPublishTime(String time) {
        mPublishTime = time.substring(0, time.indexOf(' '));
    }

    public String getPublishTime() {
        return mPublishTime;
    }

    public void setVol(int vol) {
        mVol = vol;
    }

    public int getVol() {
        return mVol;
    }

    public void onShow() {
        LogUtils.debug(TAG, "onShow, ");
    }

    public void onClick() {
        LogUtils.debug(TAG, "onClick, " + mTitle);
        ARouter.getInstance().build(Constant.ARouter.MAGAZINE_DETAIL_PATH)
            .withInt(Constant.ARouter.PARAM_ID, getId()).navigation();
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{MagazineItem, mId = %d, mTitle = %s, mPublishTime = %s, mVol = %d}",
            mId, mTitle, mPublishTime, mVol);
    }
}
