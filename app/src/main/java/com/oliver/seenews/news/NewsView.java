package com.oliver.seenews.news;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.base.widget.LoadingDialog;
import com.oliver.seenews.news.domain.page.NewsPage;
import com.oliver.seenews.news.page.NewsPageFragment;

public class NewsView implements NewsContract.View {

    private static final String TAG = "NewsView";

    private NewsContract.Presenter mPresenter;
    private Fragment mFragment;
    private LoadingDialog mLoadingDialog;
    private TabLayout mNewsTab;
    private ViewPager mViewPager;

    public NewsView(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingPage() {
        mLoadingDialog = new LoadingDialog();
        LogUtils.debug(TAG, mFragment.getChildFragmentManager().toString());
        mLoadingDialog.show(mFragment.getChildFragmentManager(), TAG);
    }

    @Override
    public void showPage(@NonNull List<NewsPage> newsPages) {
        init(newsPages);
        mLoadingDialog.dismiss();
    }

    private void init(@NonNull List<NewsPage> newsPages) {
        mNewsTab = mFragment.getView().findViewById(R.id.news_tabs);
        mNewsTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager = mFragment.getView().findViewById(R.id.news_viewpager);
        mViewPager.setAdapter(new NewsPageAdapter(mFragment.getChildFragmentManager(), newsPages));
        mNewsTab.setupWithViewPager(mViewPager);
        mNewsTab.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                LogUtils.debug(TAG, "onTabSelected " + tab.getText());
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
        //mViewPager.setOffscreenPageLimit(newsPages.size());
    }

    private static class NewsPageAdapter extends FragmentPagerAdapter {

        private List<NewsPage> mNewsPages;
        private SparseArray<Fragment> mNewsPageFragments;

        public NewsPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<NewsPage> newsPages) {
            super(fragmentManager);
            mNewsPages = newsPages;
            mNewsPageFragments = new SparseArray<>();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mNewsPageFragments.get(position);
            if (null == fragment) {
                fragment = NewsPageFragment.createInstance(mNewsPages.get(position).getTitle().getId());
                mNewsPageFragments.put(position, fragment);
            }
            LogUtils.debug(TAG, "getItem " + position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mNewsPages.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsPages.get(position).getTitle().getName();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}
