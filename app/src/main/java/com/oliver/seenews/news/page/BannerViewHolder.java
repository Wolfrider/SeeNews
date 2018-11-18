package com.oliver.seenews.news.page;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.bumptech.glide.Glide;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.widget.BannerIndicator;
import com.oliver.seenews.news.domain.page.BannerCard;
import com.oliver.seenews.news.domain.page.BannerCardItem;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.UltraViewPagerAdapter;

public class BannerViewHolder extends BaseNewsPageViewHolder<BannerCard> {

    private static final String TAG = "BannerViewHolder";

    private BannerCard mBannerCard;

    private UltraViewPager mViewPager;
    private BannerIndicator mIndicator;

    public static BannerViewHolder create(@NonNull NewsPageViewContext viewContext, @NonNull ViewGroup parent) {
        return new BannerViewHolder(viewContext,
            LayoutInflater.from(viewContext.getContext()).inflate(R.layout.news_page_banner, parent, false));
    }

    private BannerViewHolder(NewsPageViewContext viewContext, View itemView) {
        super(viewContext, itemView);
        mViewPager = itemView.findViewById(R.id.news_page_view_pager);
        mIndicator = itemView.findViewById(R.id.news_page_banner_indicator);
    }

    @Override
    public void bindData(BannerCard bannerCard) {
        LogUtils.debug(TAG, "bindData");
        mBannerCard = bannerCard;
        fillBanner();
    }

    @Override
    public void onShow() {
        if (mViewContext.isShowing()) {
            mViewPager.setAutoScroll(3000);
        } else {
            mViewPager.disableAutoScroll();
        }
    }

    @Override
    public void onLeft() {
        mViewPager.disableAutoScroll();
    }

    private void fillBanner() {
        mViewPager.setAdapter(new UltraViewPagerAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mBannerCard.getItems().size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                final BannerCardItem cardItem = mBannerCard.getItems().get(position);
                View view = LayoutInflater.from(mViewContext.getContext()).inflate(R.layout.banner_card_layout, null);
                AppCompatImageView imageView = view.findViewById(R.id.banner_image);
                Glide.with(mViewContext.getContext()).load(cardItem.getImageUrl()).into(imageView);
                AppCompatTextView textView = view.findViewById(R.id.banner_title);
                textView.setText(cardItem.getTitle());
                container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardItem.onClick();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            }
        }));
        mViewPager.setInfiniteLoop(true);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //LogUtils.debug(TAG, "onPageSelected " + mViewPager.getCurrentItem());
                mIndicator.setCurrent(mViewPager.getCurrentItem());
                mBannerCard.setCurrent(mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicator.setCount(mViewPager.getAdapter().getCount());
        mIndicator.setCurrent(0);
    }
}
