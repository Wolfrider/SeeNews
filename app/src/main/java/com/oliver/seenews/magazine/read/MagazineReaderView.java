package com.oliver.seenews.magazine.read;

import java.util.ArrayList;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.oliver.seenews.R;
import com.oliver.seenews.base.dmm.DMMPageView;
import com.oliver.seenews.base.dmm.DMMPageView.OnFocusClickListener;
import com.oliver.seenews.base.dmm.document.Magazine;
import com.oliver.seenews.base.dmm.document.Page;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.read.MagazineReaderContract.Presenter;
import com.oliver.seenews.magazine.read.nav.MagazineNavDialog;
import com.oliver.seenews.magazine.read.nav.MagazineNavDialog.OnItemSelectedListener;

public class MagazineReaderView implements MagazineReaderContract.View {

    private static final String TAG = "MagazineReaderView";

    private MagazineReaderContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private AppCompatImageView mNavView;
    private DMMPageView.OnFocusClickListener mOnFocusClickListener;

    private String mTitle;
    private String mLocalDirPath;
    private Magazine mMagazine;

    public MagazineReaderView(@NonNull AppCompatActivity activity, @NonNull String title) {
        mActivity = activity;
        mTitle = title;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void init() {
        //mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //mActivity.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        mActivity.setContentView(R.layout.magazine_reader_activity);
        mViewPager = mActivity.findViewById(R.id.magazine_reader_view_pager);
        mToolbar = mActivity.findViewById(R.id.magazine_reader_toolbar);
        mNavView = mActivity.findViewById(R.id.magazine_reader_nav);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.detail_close);
        mActivity.getSupportActionBar().setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        initOnFocusClickListener();
        initNavView();
    }

    @Override
    public void load(@NonNull final String localDirPath, @NonNull final Magazine magazine) {
        mLocalDirPath = localDirPath;
        mMagazine = magazine;
        LogUtils.debug(TAG, "load, page size = " + mMagazine.getPages().size());
        mViewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                LogUtils.debug(TAG, "instantiateItem, position = " + position);
                Page page = mMagazine.getPages().get(position);
                DMMPageView view = new DMMPageView(mActivity);
                view.setData(mLocalDirPath, page, mMagazine.getWidth(), mMagazine.getHeight());
                view.setOnFocusClickListener(mOnFocusClickListener);
                container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
                return mMagazine.getPages().size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                LogUtils.debug(TAG, "destroyItem, position = " + position);
                container.removeView((View)object);
            }
        });
    }

    private void initOnFocusClickListener() {
        mOnFocusClickListener = new OnFocusClickListener() {
            @Override
            public void onClick(String dest) {
                if (null == mMagazine) {
                    return;
                }
                for (int i = 0; i < mMagazine.getPages().size(); ++i) {
                    if (TextUtils.equals(mMagazine.getPages().get(i).getName(), dest)) {
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        };
    }

    private void initNavView() {
        mNavView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mMagazine) {
                    return;
                }
                final MagazineNavDialog dialog = MagazineNavDialog.create(getThumbnails(), mViewPager.getCurrentItem());
                dialog.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onSelected(int index) {
                        LogUtils.debug(TAG, "onSelected, " + index);
                        mViewPager.setCurrentItem(index);
                        dialog.dismiss();
                    }
                });
                dialog.show(mActivity.getSupportFragmentManager(), TAG);
            }
        });
    }

    private ArrayList<String> getThumbnails() {
        ArrayList<String> thumbnails = new ArrayList<>();
        for (Page page: mMagazine.getPages()) {
            thumbnails.add(FileUtils.joinPath(mLocalDirPath, page.getThumbnail()));
        }
        return thumbnails;
    }

}
