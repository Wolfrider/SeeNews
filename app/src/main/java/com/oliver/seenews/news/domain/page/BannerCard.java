package com.oliver.seenews.news.domain.page;

import java.util.List;

import com.oliver.seenews.base.util.LogUtils;

public class BannerCard implements INewsPageCard {

    private static final String TAG = "BannerCard";

    private List<BannerCardItem> mItems;
    private int mCurrentIndex;

    public BannerCard(List<BannerCardItem> items) {
        mItems = items;
        mCurrentIndex = 0;
    }

    @Override
    public int getType() {
        return CardType.BANNER;
    }

    @Override
    public void onShow() {
        LogUtils.debug(TAG, "onShow");
    }

    @Override
    public void onLeft() {
        LogUtils.debug(TAG, "onLeft");
    }

    public List<BannerCardItem> getItems() {
        return mItems;
    }

    public void setCurrent(int index) {
        if (mCurrentIndex >= 0) {
            mItems.get(mCurrentIndex).onLeft();
        }
        mCurrentIndex = index;
        mItems.get(mCurrentIndex).onShow();
    }
}
