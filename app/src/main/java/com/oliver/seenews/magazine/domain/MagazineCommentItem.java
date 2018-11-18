package com.oliver.seenews.magazine.domain;

import java.util.Locale;

import com.oliver.seenews.base.util.LogUtils;

public class MagazineCommentItem {

    private static final String TAG = "MagazineCommentItem";

    private String mAvatarUrl;
    private String mUserName;
    private String mPublishTime;
    private String mDesc;
    private int mScorePoint;

    public MagazineCommentItem() {
    }

    public void setAvatarUrl(String url) {
        mAvatarUrl = url;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setPublishTime(String publishTime) {
        mPublishTime = publishTime;
    }

    public String getPublishTime() {
        return mPublishTime;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setScorePoint(int point) {
        mScorePoint = point;
    }

    public int getScorePoint() {
        return mScorePoint;
    }

    public void onShow() {
        LogUtils.debug(TAG, "onShow, " + mUserName);
    }

    public void onLeft() {
        LogUtils.debug(TAG, "onLeft, " + mUserName);
    }

    @Override
    public String toString() {
        return String.format(Locale.CHINA, "{MagazineCommentItem, mUserName = %s, mPublishTime = %s, mDesc = %s}",
            mUserName, mPublishTime, mDesc);
    }
}
