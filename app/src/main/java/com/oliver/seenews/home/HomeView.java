package com.oliver.seenews.home;

import java.util.Locale;

import android.arch.core.util.Function;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;
import com.oliver.seenews.R;
import com.oliver.seenews.base.util.AppUtils;
import com.oliver.seenews.base.util.LogUtils;
import com.oliver.seenews.magazine.MagazineFragment;
import com.oliver.seenews.news.NewsFragment;

public class HomeView implements HomeContract.View {

    private static final String TAG = "HomeView";

    private HomeContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private AppCompatTextView mAppVersionTextView;
    private Fragment mCurrentFragment;

    public HomeView(@NonNull AppCompatActivity activity){
        mActivity = activity;
    }

    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPage() {
        init();
        showNewsPage();
    }

    @Override
    public void showClearTips(double size) {
        String text = String.format(Locale.CHINA,
            mActivity.getResources().getString(R.string.clear_tips), size);
        Snackbar snackbar = Snackbar.make(mActivity.getWindow().getDecorView(),
            text, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void init() {
        mActivity.setContentView(R.layout.home_activity);
        initToolBar();
        initNavigationView();
        initNavigationHeader();
    }

    private void initToolBar() {
        mToolBar = mActivity.findViewById(R.id.home_toolbar);
        mActivity.setSupportActionBar(mToolBar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = mActivity.findViewById(R.id.home_drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolBar, 0, 0);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initNavigationView() {
        mNavigationView = mActivity.findViewById(R.id.home_navigation);
        mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_navigation_menu_news:
                        showNewsPage();
                        break;
                    case R.id.home_navigation_menu_magazine:
                        showMagazinePage();
                        break;
                    case R.id.home_navigation_menu_clear:
                        clear();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initNavigationHeader() {
        mAppVersionTextView = mNavigationView.getHeaderView(0).findViewById(R.id.home_navigation_header_version);
        mAppVersionTextView.setText(String.format(Locale.US, "v %s", AppUtils.getVersionName()));
    }

    private void showNewsPage() {
        mNavigationView.setCheckedItem(R.id.home_navigation_menu_news);
        mActivity.getSupportActionBar().setTitle(R.string.home_news);
        switchFragment(new Function<Void, Fragment>() {
            @Override
            public Fragment apply(Void input) {
                return new NewsFragment();
            }
        }, NewsFragment.class.getName());
    }

    private void showMagazinePage() {
        mNavigationView.setCheckedItem(R.id.home_navigation_menu_magazine);
        mActivity.getSupportActionBar().setTitle(R.string.home_magazine);
        switchFragment(new Function<Void, Fragment>() {
            @Override
            public Fragment apply(Void input) {
                return new MagazineFragment();
            }
        }, MagazineFragment.class.getName());
    }

    private void clear() {
        //mNavigationView.setCheckedItem(R.id.home_navigation_menu_clear);
        mPresenter.onClear();
    }

    private void switchFragment(Function<Void, Fragment> creator, String tag) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (null != fragment && mCurrentFragment == fragment) {
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null == fragment) {
            fragment = creator.apply(null);
            fragmentTransaction.add(R.id.home_container, fragment, tag);
            LogUtils.debug(TAG, "add, " + fragment);
        } else {
            fragmentTransaction.show(fragment);
            LogUtils.debug(TAG, "show, " + fragment);
        }
        if (null != mCurrentFragment) {
            fragmentTransaction.hide(mCurrentFragment);
            LogUtils.debug(TAG, "hide, " + mCurrentFragment);
        }
        fragmentTransaction.commit();
        mCurrentFragment = fragment;
    }

}
