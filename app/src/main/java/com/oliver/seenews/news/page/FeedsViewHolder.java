package com.oliver.seenews.news.page;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.news.domain.page.FeedsCard;

public class FeedsViewHolder extends BaseNewsPageViewHolder<FeedsCard> {

    private static final String TAG = "FeedsViewHolder";

    private FeedsCard mFeedsCard;

    private AppCompatImageView mImageView;
    private AppCompatTextView mTitleTextView;
    private AppCompatTextView mIntroTextView;

    public static FeedsViewHolder create(@NonNull NewsPageViewContext viewContext, @NonNull ViewGroup parent) {
        return new FeedsViewHolder(viewContext,
            LayoutInflater.from(viewContext.getContext()).inflate(R.layout.news_page_feed, parent, false));
    }

    private FeedsViewHolder(NewsPageViewContext viewContext, View itemView) {
        super(viewContext, itemView);
        mImageView = itemView.findViewById(R.id.news_page_feed_image);
        mTitleTextView = itemView.findViewById(R.id.news_page_feed_title);
        mIntroTextView = itemView.findViewById(R.id.news_page_feed_intro);
    }

    @Override
    public void bindData(FeedsCard feedsCard) {
        LogUtils.debug(TAG, "bindData");
        mFeedsCard = feedsCard;
        fillFeeds();
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onLeft() {

    }

    private void fillFeeds() {
        Glide.with(mViewContext.getContext()).load(mFeedsCard.getImageUrl()).into(mImageView);
        mTitleTextView.setText(mFeedsCard.getTitle());
        mIntroTextView.setText(mFeedsCard.getIntro());
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedsCard.onClick();
            }
        });
    }
}
