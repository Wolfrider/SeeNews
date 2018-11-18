package com.oliver.seenews.magazine.detail;

import java.util.Locale;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import com.bumptech.glide.Glide;
import com.oliver.seenews.R;
import com.oliver.seenews.base.rxjava.BaseObserver;
import com.oliver.seenews.base.util.AppUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.base.widget.LoadingDialog;
import com.oliver.seenews.magazine.detail.MagazineDetailContract.Presenter;
import com.oliver.seenews.magazine.detail.comments.MagazineDetailCommentsFragment;
import com.oliver.seenews.magazine.detail.content.MagazineDetailContentFragment;
import com.oliver.seenews.magazine.domain.MagazineDetailItem;
import com.oliver.seenews.widget.DownloadButton;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MagazineDetailView implements MagazineDetailContract.View {

    private static final String TAG = "MagazineDetailView";

    private MagazineDetailContract.Presenter mPresenter;

    private AppCompatActivity mActivity;
    private LoadingDialog mLoadingDialog;

    private AppCompatImageView mCoverImageView;
    private AppCompatTextView mTitleView;
    private AppCompatTextView mPublishView;
    private AppCompatTextView mSizeView;
    private AppCompatRatingBar mRatingBar;
    private DownloadButton mDownloadButton;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public MagazineDetailView(@NonNull AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        mActivity.setContentView(R.layout.magazine_detail_page_activity);
        initToolBar();
        initTitleView();
        initContentView();
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(mActivity.getSupportFragmentManager(), TAG);
    }

    @Override
    public void showPage(MagazineDetailItem detailItem) {
        mLoadingDialog.dismiss();
        Glide.with(mActivity).load(detailItem.getCoverImageUrl()).into(mCoverImageView);
        mTitleView.setText(detailItem.getTitle());
        mPublishView.setText(detailItem.getPublishDate());
        mSizeView.setText(detailItem.getSize());
        mRatingBar.setRating(detailItem.getScorePoint() * 1.0F /2);
        mRatingBar.setVisibility(View.VISIBLE);
        initDownloadButton(detailItem);
        mViewPager.setAdapter(new ContentPageAdapter(mActivity.getSupportFragmentManager(), detailItem));
        mTabLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setupWithViewPager(mViewPager);
        LogUtils.debug(TAG, "showPage");
    }

    @Override
    public void showError() {
        mLoadingDialog.dismiss();
        LogUtils.debug(TAG, "showError");
    }

    private void initToolBar() {
        Toolbar toolbar = mActivity.findViewById(R.id.magazine_detail_toolbar);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle(R.string.magazine_detail_title);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.detail_close);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    private void initTitleView() {
        mCoverImageView = mActivity.findViewById(R.id.magazine_detail_image);
        mTitleView = mActivity.findViewById(R.id.magazine_detail_title);
        mPublishView = mActivity.findViewById(R.id.magazine_detail_publish);
        mSizeView = mActivity.findViewById(R.id.magazine_detail_size);
        mRatingBar = mActivity.findViewById(R.id.magazine_detail_rating);
        mDownloadButton = mActivity.findViewById(R.id.magazine_detail_download);
    }

    private void initContentView() {
        mTabLayout = mActivity.findViewById(R.id.magazine_detail_tab_layout);
        mViewPager = mActivity.findViewById(R.id.magazine_detail_view_pager);
    }

    private void initDownloadButton(final MagazineDetailItem detailItem) {
        if (!detailItem.hasDownloaded()) {
            mDownloadButton.setText(R.string.magazine_detail_free_download);
            mDownloadButton.setCompleteProgress(0);
        } else {
            mDownloadButton.setText(R.string.magazine_detail_start_read);
            mDownloadButton.setCompleteProgress(1.0F);
        }
        mDownloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailItem.hasDownloaded()) {
                    detailItem.onRead();
                } else {
                    detailItem.onDownload().observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<Integer>() {
                            @Override
                            public void onNext(Integer integer) {
                                super.onNext(integer);
                                mDownloadButton.setText(String.format(Locale.CHINA, "下载中 %d%%", integer));
                                mDownloadButton.setCompleteProgress(integer * 1.0F / 100);
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                                mDownloadButton.setText(R.string.magazine_detail_start_read);
                                mDownloadButton.setCompleteProgress(1.0F);
                            }
                        });
                }
            }
        });
        mDownloadButton.setVisibility(View.VISIBLE);
    }

    private static class ContentPageAdapter extends FragmentPagerAdapter {

        private static int[] sTitleRes = new int[]{R.string.magazine_detail_content, R.string.magazine_detail_comment};

        private MagazineDetailItem mDetailItem;
        private SparseArray<Fragment> mFragments;

        ContentPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull MagazineDetailItem detailItem) {
            super(fragmentManager);
            mDetailItem = detailItem;
            mFragments = new SparseArray<>();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragments.get(position);
            if (null == fragment) {
                if (0 == position) {
                    fragment = MagazineDetailContentFragment.createInstance(mDetailItem.getDesc());
                    mFragments.put(position, fragment);
                } else {
                    fragment = MagazineDetailCommentsFragment.createInstance(mDetailItem.getId());
                    mFragments.put(position, fragment);
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return sTitleRes.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return AppUtils.getApp().getResources().getString(sTitleRes[position]);
        }
    }
}
